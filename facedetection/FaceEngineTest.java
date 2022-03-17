package facedetection;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.*;
import com.arcsoft.face.toolkit.ImageInfo;
import com.arcsoft.face.toolkit.ImageInfoEx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getGrayData;
import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;


public class FaceEngineTest {
    private final static String ENGINE_PATH = "libs/ArcFace/WIN64";
    // private final static String IMAGE1_PATH = "images/yuanyeye.png";
    private final static String IMAGE1_PATH = "images/1.png";
    private final static String IMAGE2_PATH = "images/yuanyeye.png";

    public static void main(String[] args) {
        //从官网获取
        String appId = "2LbbE535y5DszNMsEWssYuzr9wdwLbuXbz3VhWW9wPqC";
        String sdkKey = "CVaw1FPskpY22Kw3H9btguH4hRWA1qPUgjuFYsyeiTgh";

        // 人脸识别引擎，参数为算法库文件夹的绝对路径
        FaceEngine faceEngine = new FaceEngine(new File(ENGINE_PATH).getAbsolutePath());
        //激活引擎
        int errorCode = faceEngine.activeOnline(appId, sdkKey);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.err.println("引擎激活失败");
        }

        // 获取激活文件信息
        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.err.println("获取激活文件信息失败");
        }

        // 引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        // 单张图像模式
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        // 检测所有角度
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        // 检测最多人脸数
        engineConfiguration.setDetectFaceMaxNum(10);
        // 设置识别的最小人脸比例，公式为 图片长边 / 人脸框长边的比值，一般是16
        engineConfiguration.setDetectFaceScaleVal(16);

        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);              // 支持年龄检测功能
        functionConfiguration.setSupportFace3dAngle(true);      // 支持3D检测功能
        functionConfiguration.setSupportFaceDetect(true);       // 支持人脸检测功能
        functionConfiguration.setSupportFaceRecognition(true);  // 支持人脸识别功能
        functionConfiguration.setSupportGender(true);           // 支持性别检测功能
        functionConfiguration.setSupportLiveness(true);         // 支持RGB活体检测功能
        functionConfiguration.setSupportIRLiveness(true);       // 支持IR活体检测功能
        engineConfiguration.setFunctionConfiguration(functionConfiguration);

        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);
        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.err.println("初始化引擎失败");
        }


        //人脸检测
        // 采集图像信息
        ImageInfo imageInfo = getRGBData(new File(IMAGE1_PATH)); //图片需要是RGB图片
        // 人脸信息列表，使用list是因为detectFaces()方法接受的参数就是list，实际上只需要一个FaceInfo对象
        List<FaceInfo> faceInfoList = new ArrayList<>();
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        if (faceInfoList.isEmpty()){
            System.err.println("图片中没有捕获到人脸，错误代码:"+errorCode);
        }
        System.out.println(faceInfoList);

        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        System.out.println("特征值大小：" + faceFeature.getFeatureData().length);

        //人脸检测2
        ImageInfo imageInfo2 = getRGBData(new File(IMAGE2_PATH));
        List<FaceInfo> faceInfoList2 = new ArrayList<>();
        errorCode = faceEngine.detectFaces(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo.getImageFormat(), faceInfoList2);
        System.out.println(faceInfoList);
        //特征提取2
        FaceFeature faceFeature2 = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo.getImageFormat(), faceInfoList2.get(0), faceFeature2);
        System.out.println("特征值大小：" + faceFeature.getFeatureData().length);

        //特征比对
        FaceFeature targetFaceFeature = new FaceFeature(); //这里又拷贝了一份，不拷贝也可以吧
        targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
        FaceSimilar faceSimilar = new FaceSimilar();
        errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
        System.out.println("相似度：" + faceSimilar.getScore());

        //设置活体测试
        errorCode = faceEngine.setLivenessParam(0.5f, 0.7f);
        //人脸属性检测
        FunctionConfiguration configuration = new FunctionConfiguration();
        configuration.setSupportAge(true);
        configuration.setSupportFace3dAngle(true);
        configuration.setSupportGender(true);
        configuration.setSupportLiveness(true);
        // RGB活体、年龄、性别、三维角度检测，在调用该函数后，可以调用get...()获取检测结果
        errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);


        //性别检测
        List<GenderInfo> genderInfoList = new ArrayList<>();
        errorCode = faceEngine.getGender(genderInfoList);
        System.out.println("性别：" + genderInfoList.get(0).getGender()); //未知性别=-1 、男性=0 、女性=1

        //年龄检测
        List<AgeInfo> ageInfoList = new ArrayList<>();
        errorCode = faceEngine.getAge(ageInfoList);
        System.out.println("年龄：" + ageInfoList.get(0).getAge()); //年龄信息，若为0表示检测失败

        //3D信息检测
        List<Face3DAngle> face3DAngleList = new ArrayList<>();
        errorCode = faceEngine.getFace3DAngle(face3DAngleList);
        System.out.println("3D角度：" + face3DAngleList.get(0).getPitch() + "," + face3DAngleList.get(0).getRoll() + "," + face3DAngleList.get(0).getYaw());

        //活体检测
        List<LivenessInfo> livenessInfoList = new ArrayList<>();
        errorCode = faceEngine.getLiveness(livenessInfoList);
        System.out.println("活体：" + livenessInfoList.get(0).getLiveness()); //RGB活体值，未知=-1 、非活体=0 、活体=1、超出人脸=-2

        //IR属性处理
        ImageInfo imageInfoGray = getGrayData(new File(IMAGE1_PATH));
        List<FaceInfo> faceInfoListGray = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfoGray.getImageData(), imageInfoGray.getWidth(), imageInfoGray.getHeight(), imageInfoGray.getImageFormat(), faceInfoListGray);

        FunctionConfiguration configuration2 = new FunctionConfiguration();
        configuration2.setSupportIRLiveness(true);
        errorCode = faceEngine.processIr(imageInfoGray.getImageData(), imageInfoGray.getWidth(), imageInfoGray.getHeight(), imageInfoGray.getImageFormat(), faceInfoListGray, configuration2);
        //IR活体检测
        List<IrLivenessInfo> irLivenessInfo = new ArrayList<>();
        errorCode = faceEngine.getLivenessIr(irLivenessInfo);
        System.out.println("IR活体：" + irLivenessInfo.get(0).getLiveness());

        ImageInfoEx imageInfoEx = new ImageInfoEx();
        imageInfoEx.setHeight(imageInfo.getHeight());
        imageInfoEx.setWidth(imageInfo.getWidth());
        imageInfoEx.setImageFormat(imageInfo.getImageFormat());
        imageInfoEx.setImageDataPlanes(new byte[][]{imageInfo.getImageData()});
        imageInfoEx.setImageStrides(new int[]{imageInfo.getWidth() * 3});
        List<FaceInfo> faceInfoList1 = new ArrayList<>();
        errorCode = faceEngine.detectFaces(imageInfoEx, DetectModel.ASF_DETECT_MODEL_RGB, faceInfoList1);

        FunctionConfiguration fun = new FunctionConfiguration();
        fun.setSupportAge(true);
        errorCode = faceEngine.process(imageInfoEx, faceInfoList1, functionConfiguration);
        List<AgeInfo> ageInfoList1 = new ArrayList<>();
        int age = faceEngine.getAge(ageInfoList1);
        System.out.println("年龄：" + ageInfoList1.get(0).getAge());

        FaceFeature feature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfoEx, faceInfoList1.get(0), feature);


        //引擎卸载
        errorCode = faceEngine.unInit();

    }
}