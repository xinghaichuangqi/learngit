package mydemo;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

//import mydemo.Xiaorili.CalendarTable;

 public class Xiaorili extends JApplet {
	//==============================================================
	/*定义全局变量*/
	public static final Color background = Color.white; //背景色
	public static final Color foreground = Color.black; //前景色
	public static final Color headerBackground = Color.DARK_GRAY; //星期
	public static final Color headerForeground = Color.white; //星期前景色
	public static final Color selectBackground = Color.green; //选中背景色
	public static final Color selectForeground = Color.white; //选中前景色
	
	public static final String WeekSun ="星期日";//星期标签名称
	public static final String WeekMon ="星期一";
	public static final String WeekTue ="星期二";
	public static final String WeekWed ="星期三";
	public static final String WeekThu ="星期四";
	public static final String WeekFri ="星期五";
	public static final String WeekSat ="星期六";
	
	private JPanel MainPanel;//日历面板
	private JLabel yearsLabel;//年份标签
	private JSpinner yearsSpinner;//年份组合框
	private JLabel monthsLabel;//月份标签
	private JComboBox monthsComboBox;//十二月份下拉框
	private JLabel textLabel;//标题显示标签
	private JLabel InfoLabel;//个人信息显示标签
	//private JLabel daysLabel;//日表格
    private AbstractTableModel daysModel;//天单元表格
    private Calendar calendar;//日历对象
    //=======================================================
    /*函数定义*/
    public Xiaorili() {         //构造函数
    	MainPanel = (JPanel) getContentPane();
    }
    
    public void init(){   //初始化面板界面函数
    	MainPanel.setLayout(new BorderLayout());
    	calendar=Calendar.getInstance();  //默认方式，以本地时区和地区来构造Calendar
    	//-------------------------------------------------------------------------
    	yearsLabel=new JLabel("年份：");//设置年份标签
    	yearsSpinner =new JSpinner();//构造年份spinner组合框
    	yearsSpinner.setEditor(new JSpinner.NumberEditor(yearsSpinner, "0000"));
    	yearsSpinner.setValue(new Integer(calendar.get(calendar.YEAR)));
    	yearsSpinner.addChangeListener(new ChangeListener(){  //注册该组合框的事件监听
    		
    		public void stateChanged(ChangeEvent changeEvent){
    			int day=calendar.get(Calendar.DAY_OF_MONTH);
    			calendar.set(Calendar.DAY_OF_MONTH,1);
    			calendar.set(Calendar.YEAR, ((Integer)yearsSpinner.getValue()).intValue());
    		
    		
    		int maxDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    		calendar.set(Calendar.DAY_OF_MONTH, day>maxDay?maxDay:day);
    		updateView();  //更新显示
    		}
    	});
    	//--------------------------------------------------------------------------------
    	JPanel yearMonthPanel =new JPanel();
    	MainPanel.add(yearMonthPanel, BorderLayout.NORTH);// 添加年月面板到日历面板的南面（最上方）
    	yearMonthPanel.setLayout(new BorderLayout());// 边布局模式
    	JPanel yearPanel = new JPanel();// 构建年份面板
    	yearMonthPanel.add(yearPanel, BorderLayout.WEST);// 年份面板添加到年月面板西部（左边）
    	yearPanel.setLayout(new BorderLayout());// 设置年份面板为边布局并添加年份标签和组合框
    	yearPanel.add(yearsLabel, BorderLayout.WEST);
    	yearPanel.add(yearsSpinner, BorderLayout.CENTER);
    	// --------------------------------------
    	monthsLabel = new JLabel("月份: "); // 设置月份标签显示
    	monthsComboBox = new JComboBox();// 月份下拉框
    	for (int i = 1; i <= 12; i++) { // 构造下拉框的12个月份
    	monthsComboBox.addItem(new Integer(i));
    	}
    	monthsComboBox.setSelectedIndex(calendar.get(Calendar.MONTH));// 下拉框当前月份为选中状态
    	monthsComboBox.addActionListener(new ActionListener() { // 注册月份下拉框的事件监听器
    	public void actionPerformed(ActionEvent actionEvent) {
    	int day = calendar.get(Calendar.DAY_OF_MONTH);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	calendar.set(Calendar.MONTH, monthsComboBox
    	.getSelectedIndex());
    	int maxDay = calendar
    	.getActualMaximum(Calendar.DAY_OF_MONTH);
    	calendar.set(Calendar.DAY_OF_MONTH,
    	day > maxDay ? maxDay : day);
    	updateView();// 更新面板显示
    	}
    	});
    	// -----------------------------------------------------------------
    	JPanel monthPanel = new JPanel();// 定义月份面板
    	yearMonthPanel.add(monthPanel, BorderLayout.EAST);// 添加月份面板到年月面板的东面（右面）
    	monthPanel.setLayout(new BorderLayout());// 月份面板设为边布局方式
    	monthPanel.add(monthsLabel, BorderLayout.WEST);// 添加月份名称标签到月份面板西面（左面）
    	monthPanel.add(monthsComboBox, BorderLayout.CENTER);// 添加月份下拉框到月份面板中间
    	// --------------------------------------
    	textLabel = new JLabel("JAVA小日历"); // 设置标题标签显示
    	JPanel txetPanel = new JPanel();// 定义标题文本显示面板
    	yearMonthPanel.add(txetPanel, BorderLayout.CENTER);// 添加标题文本显示面板到年月面板中间
    	txetPanel.add(textLabel, BorderLayout.CENTER);// 添加标题文本标签到面板
    	// --------------------------------------
    	InfoLabel = new JLabel("浪潮信息      系统软件部      姜文涛    "); // 设置个人信息标签显示
    	JPanel InfoPanel = new JPanel();// 定义底部个人信息显示面板
    	MainPanel.add(InfoPanel, BorderLayout.SOUTH);// 添加个人信息显示面板到日历面板南方（下方）
    	InfoPanel.add(InfoLabel);// 添加信息标签文本标签到面板
        daysModel = new AbstractTableModel() { // 设置7行7列
    	public int getRowCount() {
    	                   return 7;
                                	}
    	public int getColumnCount() {
    	                  return 7;
    	                             }
    	public Object getValueAt(int row, int column) {
    	  if (row == 0) { // 第一行显示星期
    	               return getHeader(column);
    	                }
    	          row--;
    	    Calendar calendar = (Calendar) Xiaorili.this.calendar.clone();
    	    calendar.set(Calendar.DAY_OF_MONTH, 1);
    	    int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    	    int moreDayCount = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    	    int index = row * 7 + column;
    	    int dayIndex = index - moreDayCount + 1;
    	    if (index < moreDayCount || dayIndex > dayCount) {
        	                 return null;
    	          } else {
                         	return new Integer(dayIndex);
                        	}
               }
          };
        CalendarTable daysTable = new CalendarTable(daysModel, calendar); // 构造日表格
        daysTable.setCellSelectionEnabled(true);// 设置表格单元格可选择
        daysTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		daysTable.setDefaultRenderer(daysTable.getColumnClass(0),
				new TableCellRenderer() {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column) {
    String text = (value == null) ? "" : value.toString();
    JLabel cell = new JLabel(text);
    cell.setOpaque(true); // 绘制边界内的所有像素
    if (row == 0) { // 第一行显示星期，设置为星期的前景色和背景色
    cell.setForeground(headerForeground);
    cell.setBackground(headerBackground);
    } else {
    if (isSelected) { // 日期单元格如果选中，则设置为日期选中的前、背 景色
    cell.setForeground(selectForeground);
    cell.setBackground(selectBackground);
    } else { // 设置日期单元格的普通前、背景色
    cell.setForeground(foreground);
    cell.setBackground(background);
    }
    }return cell;
    }
    });
    updateView();
    MainPanel.add(daysTable, BorderLayout.CENTER);// 添加日面板到日历面板中间
    }
    // --------------------------------------
    public static String getHeader(int index) {// 设置第一行星期的显示
			    switch (index) {
					    case 0:
					    return WeekSun;
					    case 1:
					    return WeekMon;
					    case 2:
					    return WeekTue;
					    case 3:
					    return WeekWed;
					    case 4:
					    return WeekThu;
					    case 5:
					    return WeekFri;
					    case 6:
					    return WeekSat;
					    default:
					    return null;
			    }
    		}
    	// --------------------------------------
	  public void updateView() {// 更新面板显示方法
	    daysModel.fireTableDataChanged();
	    
	    
	    CalendarTable daysTable = new CalendarTable(daysModel, calendar); // 构造日表格
		daysTable.setRowSelectionInterval(calendar.get(Calendar.WEEK_OF_MONTH),calendar.get(Calendar.WEEK_OF_MONTH));
	    daysTable.setColumnSelectionInterval(calendar.get(Calendar.DAY_OF_WEEK) - 1, calendar.get(Calendar.DAY_OF_WEEK) - 1);
	    }
		    
		    
		    public static class CalendarTable extends JTable {// 表格类
		    private Calendar calendar;
		    public CalendarTable(TableModel model, Calendar calendar) {// 构造方法
		    super(model);
		    this.calendar = calendar;	
		    }
		    
    
		    public void changeSelection(int row, int column, boolean toggle,
    boolean extend) {// 选择表格单元格时
    super.changeSelection(row, column, toggle, extend);
    if (row == 0) {// 选择为第一行（星期）时不改变单元格
    return;
    }
    Object obj = getValueAt(row, column);
    if (obj != null) {
    calendar.set(Calendar.DAY_OF_MONTH, ((Integer) obj).intValue());
    }
    }	
		    }
	
	
	
	
			}

		/*java Calendar 方法：Calendar 抽象类定义了足够的方法，让我们能够表述日历的规则。我们也可以
		自己的Calendar 实现类，然后将它作为Calendar 对象返回(面向对象的特性)。在Calendar 的方法中，
		get() 和add() 会让Calendar 立刻刷新。本程序的中心设计即是利用Calendar 类来实现。
		javax swing JSpinner 用法：让用户从一个有序序列中选择一个数字或者一个对象值的单行输入字段。
		它能够方便地选择日期、数字或列表中的选项。Spinner 通常提供一对带小箭头的按钮以便逐步遍历序列
		元素。键盘的向上/向下方向键也可循环遍历元素。也允许用户在Spinner 中直接输入合法值。JSpinner
		显示不同的内容，采用不同的模型，像SpinnerDateModel，SpinnerListModel，SpinnerNumberModel，只
		要对模型修改，就可以实现想要的精确的功能。本程序利用该组件实现了年份的修改。
		setOpaque 用法：setOpaque (false)的作用是将button 的背景色改为其parent 的背景色。如果为
		true，则该组件绘制其边界内的所有像素。否则该组件可能不绘制其某些或所有像素，从而允许其下面的
		像素透视出来。
		基于Java Swing 的超链接标签和超链接按钮的实现： java.awt.Desktop 类的
		java.awt.Desktop.isDesktopSupported()可以获取当前系统是否支持java awt 桌面扩展.如果支持可以获
		取当前系统的Desktop，则使用：
		java.awt.Desktop dp = java.awt.Desktop.getDesktop()；
		dp.browse(java.net.URI)； 就可以启动系统默认的浏览器。本程序自定义一个超链接标签控件
		[LinkLabel]类进行实现该功能。
		java 中实现表格的多表头显示：主要就是三个应用类ColumnGroup， GroupableTableHeader，
		GroupableTableHeaderU 和一个示例类GroupableHeaderExample。
		(1)本小日历设计主体结构
		设计一个框架MainFrame，然后在MainFrame 里添加一个MainPanel 面板。定义一个MainPanel 面板
		类，该面板以边布局格式，所有其他面板、标签等均添加到所设定的面板中。
		(2) MainPanel 面板北部（上方），添加一个年月面板YearMonthPanel
		该年月面板主要放年份显示、月份显示、标题显示。YearMonthPanel 面板也使用边布局格式，西部添
		加一个年份面板，放年份信息；中部添加一个TextPanel 面板，放标题文本显示信息；东部添加一个月份
		面板MonthPanel，放月份下拉框信息。
		(3)MainPanel 主面板的中部添加
		添加一个DaysPanel 面板用于放置月份的天数表格信息。
		(4)MainPanel 主面板的南部（下方）添加
		添加一个InfoPanel 面板，主要用于放置显示个人信息。
		(1)设计类及对象设计
		在本程序中：定义了一个Frame 类，主要定义一个窗体框架，用于显示日历信息，并在主函数中只创
		建一个该窗体对象实现程序功能。
		定义了一个主日历面板类Xiaorili，其中包含了日历设计所用到的全局变量、功能函数等，在Frame
		类里定义了其对象。在该日历面板类里又设计了一个内部类CalendarTable 表格类，用于布局日历天的显
		示格式。
		为了设计一个超级链接，定义了一个LinkLable 类，单独保存在一个文件LinkTbale.java 中，在日历
		面板类Xiaorili 里定义了其对象。
		(2)程序设计思路
		①.总天数的算法：首先用if 语句判断定义年到输入年之间每一年是否为闰年，是闰年，该年的总天
		数为366，否则，为365。然后判断输入的年是否为定义年，若是，令总天数S=1，否则，用累加法计算出
		定义年到输入年之间的总天数，再把输入年的一月到要输出的月份之间的天数累加起来，若该月是闰年中
		的月份并且该月还大于二月，再使总天数加1，否则，不加，既算出从定义年一月一日到输出年的该月一日
		的总天数。
		②.输出月份第一天为星期几的算法：使总天数除以7 取余加2 得几既为星期几，若是7，则为星期日。
		③.算出输出月份第一天为星期几的算法：算出输出月份第一天为星期几后，把该日期以前的位置用空
		格补上，并总该日起一次输出天数直到月底，该月中的天数加上该月一日为星期几的数字再除以7 得0 换
		行，即可完整的输出该月的日历。
		④.如果年份小于1582 年则程序不予判断。
		*/
