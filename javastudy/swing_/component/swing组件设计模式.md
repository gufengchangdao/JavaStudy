swing组件使用模型-视图-控制器设计模式
=================================

1. 模型、视图和控制器之间的交互
```
控制器 --绘制视图--> 视图 --读取内容--> 模型
控制器 --更新内容--> 模型 --内容改变--> 视图
控制器 --更新视图--> 视图
```
2. 大多数组件的模型类会实现一个以Model结尾的接口，比如ButtonModel接口，其中DefaultButtonModel类实现了该接口
3. 对于JButton类来说，用BasicButtonUI类作为视图，用ButtonUIListener类作为控制器，用DefaultButtonModel类作为模型