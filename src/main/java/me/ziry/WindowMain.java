package me.ziry;

import me.ziry.util.ReNameUtil;
import org.pushingpixels.substance.api.skin.AutumnSkin;
import org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * 窗口界面程序
 * @author Ziry
 */
public class WindowMain extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private static final String VERSION = "V1.0";

    /**文件类型标记**/
    private static final String FILE_TYPE_MARK = ".";

    /**规则文本长度**/
    private static final int RULE_TEXT_LENGTH = 200;

    JFileChooser jFileChooser = new JFileChooser();

    /** 按钮集合 **/
    JButton sequence_btn = new JButton("序号");
    JButton old_file_name_btn = new JButton("原文件名");
    JButton old_file_suffix_btn = new JButton("原文件后缀");

    JButton create_time_l14_btn = new JButton("创建时间:yyyyMMddHHmmss");
    JButton create_time_l8_btn = new JButton("创建时间:yyyyMMdd");

    JButton in_btn = new JButton("选择操作目录");
    JButton out_btn = new JButton("选择输出目录");
    JButton enter_btn = new JButton("确定");

    /**表达式输入框**/
    JTextField rule_text = new JTextField("【原文件名】.【原文件后缀】");

    /**隐藏输入框，用于目录路径保存**/
    JTextField in_text = new JTextField();
    JTextField out_text = new JTextField();

    /** 界面宽度 */
    private final int WIDTH = 500;
    /** 界面高度*/
    private final int HEIGHT = 300;

    /** 设置用户界面居中 **/
    private Toolkit tk = Toolkit.getDefaultToolkit();
    private Dimension d = tk.getScreenSize();
    private int y = d.height / 2 - HEIGHT / 2;
    private int x = d.width / 2 - WIDTH / 2;

    public WindowMain() {

        // 界面标题
        this.setTitle("Ziry作品:RanameTool" );

        // 设置界面位置及大小，这里使用居中位置x,y
        this.setBounds(x, y, WIDTH, HEIGHT);

        //设置图标
        Image imageIco = Toolkit.getDefaultToolkit().getImage(
                WindowMain.class.getClassLoader().getResource("logo.png"));
        this.setIconImage(imageIco);

        // 网格布局
        GridLayout gridLayout = new GridLayout(7, 1,0,0);
        this.setLayout(gridLayout);

        //欢迎提示
        JPanel title_pan = new JPanel();
        title_pan.setLayout(new FlowLayout(FlowLayout.CENTER, 10,12));
        JLabel title = new JLabel("欢迎使用：《批量修改文件名"+ VERSION +"》 小工具");
        title.setFont(new java.awt.Font("黑体", 1, 14));
        title_pan.add(title);
        this.add(title_pan);

        JPanel top_pan_one = new JPanel();
        top_pan_one.setLayout(new FlowLayout(FlowLayout.LEFT,2,2));
        top_pan_one.add(new JLabel("根据规则表达式，选择输入及输出目录，即可生成指定文件名规则的拷贝文件"));

        JPanel top_pan_two = new JPanel();
        top_pan_two.setLayout(new FlowLayout(FlowLayout.LEFT,2,2));
        top_pan_two.add( new JLabel("请编写规则表达式，点击下方按钮可插入【填充占位符】："));

        JPanel top_pan = new JPanel();
        top_pan.setLayout(new GridLayout(2, 1) );
        top_pan.add(top_pan_one);
        top_pan.add(top_pan_two);
        this.add(top_pan);

        //第一排按钮
        JPanel btn_pan1 = new JPanel();
        btn_pan1.setLayout( new GridLayout(1, 3) );
        btn_pan1.add(sequence_btn);
        btn_pan1.add(old_file_name_btn);
        btn_pan1.add(old_file_suffix_btn);
        this.add(btn_pan1);

        //第二排按钮
        JPanel btn_pan2 = new JPanel();
        btn_pan2.setLayout( new GridLayout(1, 2) );
        btn_pan2.add(create_time_l14_btn);
        btn_pan2.add(create_time_l8_btn);
        this.add(btn_pan2);

        //规则输入框
        in_text.setVisible(false);
        out_text.setVisible(false);
        this.add(rule_text);

        //输入目录
        JPanel folder_pan = new JPanel();
        folder_pan.setLayout(new GridLayout(1, 2) );
        folder_pan.add(in_btn);
        folder_pan.add(out_btn);
        this.add(folder_pan);

        //确认按钮
        this.add(enter_btn);

        // 设置文件选择器标题
        jFileChooser.setDialogTitle("请选择目录");
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setMultiSelectionEnabled(false);

        // 设置按钮监听
        sequence_btn.addActionListener(this);
        old_file_name_btn.addActionListener(this);
        old_file_suffix_btn.addActionListener(this);
        create_time_l14_btn.addActionListener(this);
        create_time_l8_btn.addActionListener(this);
        in_btn.addActionListener(this);
        out_btn.addActionListener(this);
        enter_btn.addActionListener(this);

        // 设置屏幕关闭事件
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // 显示界面
        this.setVisible(true);
    }

    /**
     * 点击事件
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //序号
        if(e.getSource() == sequence_btn) {
            this.appendToName(ReNameUtil.SEQUENCE);
            return;
        }
        //原文件名
        if(e.getSource() == old_file_name_btn) {
            this.appendToName(ReNameUtil.OLD_FILE_NAME);
            return;
        }
        //原文件后缀
        if(e.getSource() == old_file_suffix_btn) {
            this.appendToType(FILE_TYPE_MARK + ReNameUtil.OLD_FILE_SUFFIX);
            return;
        }
        //创建时间:yyyyMMddHHmmss
        if(e.getSource() == create_time_l14_btn) {
            this.appendToName(ReNameUtil.CREATE_TIME_L14);
            return;
        }
        //创建时间:yyyyMMdd
        if(e.getSource() == create_time_l8_btn) {
            this.appendToName(ReNameUtil.CREATE_TIME_L8);
            return;
        }
        //选择操作目录
        if(e.getSource() == in_btn) {
            int returnVal = jFileChooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File imgFile = jFileChooser.getSelectedFile();
                in_text.setText(imgFile.toString());
                in_btn.setText("操作目录("+imgFile.toString()+")");
            }
            return;
        }
        //选择输出目录
        if(e.getSource() == out_btn) {
            int returnVal = jFileChooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File imgFile = jFileChooser.getSelectedFile();
                out_text.setText(imgFile.toString());
                out_btn.setText("输出目录("+imgFile.toString()+")");
            }
            return;
        }
        //确定
        if(e.getSource() == enter_btn) {

            /* 校验，校验参数并给用户提示 */
            //表达式
            if(rule_text.getText()==null || rule_text.getText().length()==0) {
                JOptionPane.showMessageDialog(this,
                        "请编写规则表达式", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(rule_text.getText().length() > RULE_TEXT_LENGTH) {
                JOptionPane.showMessageDialog(this,
                        "规则表达式过长（200字符以内）", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //操作目录
            if(in_text.getText()==null || in_text.getText().length()==0) {
                JOptionPane.showMessageDialog(this,
                        "请选择操作目录", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            //输出目录
            if(out_text.getText()==null || out_text.getText().length()==0) {
                JOptionPane.showMessageDialog(this,
                        "请选择输出目录", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String msg = "规则表达式："+rule_text.getText()+"\n"
                            +"操作目录:"+in_text.getText()+"\n"
                            +"输出目录:"+out_text.getText()+"\n";
            int option = JOptionPane.showConfirmDialog(this, msg, "提示", JOptionPane.YES_NO_OPTION);

            if(option == 0) {

                int result = ReNameUtil.renameByPath(in_text.getText(), out_text.getText(), rule_text.getText());

                if(result == ReNameUtil.NOT_DIRECTORY) {
                    JOptionPane.showMessageDialog(this,
                            in_text.getText()+" 不是文件夹", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(result == ReNameUtil.DIRECTORY_EMPTY) {
                    JOptionPane.showMessageDialog(this,
                            in_text.getText() +" 是空的文件夹", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(this,
                        "操作成功!", "提示", JOptionPane.PLAIN_MESSAGE);

                try {
                    Desktop.getDesktop().open(new File(out_text.getText()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                return;
            }
            return;
        }

    }

    /**
     * 文件名称追加关键字<br>
     * 如果光标不在末尾，则光标位置优先
     * @param key 关键字
     * @return
     */
    public String appendToName(String key) {

        String rule = rule_text.getText();

        //获取光标位置
        int cretPosition = rule_text.getCaretPosition();

        if(key==null || key.length() == 0) {
            return rule;
        }

        if(rule==null || rule.length() == 0) {
            rule = key;
        }
        //如果光标不在末尾，则光标位置优先
        else if(cretPosition!=rule.length()) {

            StringBuilder ruleBuilder = new StringBuilder(rule);
            ruleBuilder.insert(cretPosition, key);

            rule = ruleBuilder.toString();
        }
        else if(rule.lastIndexOf(FILE_TYPE_MARK) != -1) {
            String name = rule.substring(0, rule.lastIndexOf(FILE_TYPE_MARK));
            String type = rule.substring(rule.lastIndexOf(FILE_TYPE_MARK));
            rule = name + key + type;
        }
        else {
            rule = rule + key;
        }

        rule_text.setText(rule);
        return rule;
    }

    /**
     * 文件后缀末尾追加关键字
     * @param key 关键字
     * @return
     */
    public String appendToType(String key) {
        String rule = rule_text.getText();

        if(key==null || key.length() == 0) {
            return rule;
        }

        if(rule==null || rule.length() == 0) {
            rule = key;
        }
        else {
            rule = rule + key;
        }

        rule_text.setText(rule);
        return rule;
    }


    public static void main(String[] args) {

        /* 设置风格 */
        try {
            String lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
            UIManager.setLookAndFeel(lookAndFeel);
            // 使标题栏的风格也跟着一起改变
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //设置皮肤
        SubstanceSaharaLookAndFeel.setSkin(new AutumnSkin());

        //启动
        SwingUtilities.invokeLater( () -> new WindowMain().setVisible(true) );
    }

}