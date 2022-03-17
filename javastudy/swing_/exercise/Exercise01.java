package javastudy.swing_.exercise;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * 仿QQ登录窗口
 * 账号：ying
 * 密码：123456
 */
public class Exercise01 extends JFrame {
    public Exercise01() {
        // 设置主窗体
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(500, 250, 500, 400);
        setLayout(null);
        setTitle("用户登录界面");
        // setUndecorated(true); //这个会去掉窗口的装饰的
        setResizable(false); //禁止拖动窗口
        Container container = getContentPane();

        // 添加一个图标作为登录界面背景
        JLabel bgImg = new JLabel(new ImageIcon("images/4.jpg"));
        bgImg.setBounds(0, 0, 1000, 1000);

        // 主容器添加一个面板
        JPanel panel = new JPanel();
        panel.setBounds(45, 30, 400, 320);
        panel.setBackground(Color.getHSBColor(204.0f, 0.050251257f, 0.78039217f)); //面板背景选用灰色
        // panel.setOpaque(false); //本来想让面板背景变透明的，放弃了
        panel.setLayout(null);

        container.add(panel);
        container.add(bgImg);

        // 提前创建好需要用的组件，以便下面设置的时候先创建的组件的监听行为可以触发后创建的组件
        JButton loginLoad = new JButton("正在登陆...");
        JLabel logTip = new JLabel("用户登录", JLabel.CENTER);
        JLabel icon = new JLabel(new ImageIcon("src/javastudy/swing_/laugh.png"));
        JLabel usernameLabel = new JLabel("账号:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("密码:");
        JPasswordField passwordField = new JPasswordField();
        JButton submit = new JButton("登录");

        // 设置登录加载界面
        loginLoad.setBounds(50, 40, 300, 200);
        loginLoad.setFont(new Font("宋体", Font.PLAIN, 30));
        loginLoad.setBackground(Color.LIGHT_GRAY);
        // loginLoad.setEnabled(false); //登录加载界面是一个按钮，可以让它不能被点击
        loginLoad.setVisible(false); //初始时让按钮隐藏

        // 设置登录界面的文字
        logTip.setFont(new Font("楷体", Font.BOLD, 25));
        logTip.setBounds(0, 0, 120, 30);

        // 设置头像图标
        icon.setBounds(160, 40, 60, 60);

        // 设置账号提示标签
        usernameLabel.setBounds(70, 120, 50, 22);
        usernameLabel.setFont(new Font("楷体", Font.PLAIN, 20));

        // 设置账户输入的文本框
        usernameField.setBounds(130, 120, 160, 22);
        // 账号文本框回车事件
        usernameField.addActionListener(e -> {
            passwordField.requestFocus(); //焦点给密码文本框
        });

        // 设置密码提示标签
        passwordLabel.setBounds(70, 160, 50, 22);
        passwordLabel.setFont(new Font("楷体", Font.PLAIN, 20));

        // 设置密码输入的文本框
        passwordField.setBounds(130, 160, 160, 22);
        // 密码文本框回车事件
        passwordField.addActionListener(e -> {
            submit.doClick(); //触发按钮点击事件
        });

        // 设置登录按钮
        submit.setBounds(75, 200, 220, 30);
        submit.setFont(new Font("楷体", Font.BOLD, 20));
        submit.setBackground(Color.getHSBColor(145.44304f, 0.7745098f, 0.8f));
        // 设置登录按钮点击事件，进行账户验证
        submit.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (Objects.equals(username, "")) {
                JOptionPane.showMessageDialog(this, "请输入账号", "登录失败", JOptionPane.WARNING_MESSAGE);
                usernameField.requestFocus(); //账号错误，焦点就给账号
            } else if (Objects.equals(password, "")) {
                JOptionPane.showMessageDialog(this, "请输入密码", "登录失败", JOptionPane.WARNING_MESSAGE);
                passwordField.requestFocus();
            } else if (!username.equals("ying") || !password.equals("123456")) {
                JOptionPane.showMessageDialog(this, "账号或密码错误", "登录失败", JOptionPane.WARNING_MESSAGE);
            } else { //登录成功
                loginLoad.setVisible(true); //让登录加载组件显示出来
                usernameField.setVisible(false);
                passwordField.setVisible(false);
                submit.setVisible(false);
                // JOptionPane.showMessageDialog(this,"跳转至主界面","登录成功",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 注意使用的是绝对布局，在上面的组件会覆盖后面的组件，因此登录加载组件放在最上面
        panel.add(loginLoad);
        panel.add(logTip);
        panel.add(icon);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(submit);
    }

    public static void main(String[] args) {
        // setVisible()需要所有组件添加好之后再调用
        new Exercise01().setVisible(true);
    }
}