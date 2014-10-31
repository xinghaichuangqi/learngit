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
	/*����ȫ�ֱ���*/
	public static final Color background = Color.white; //����ɫ
	public static final Color foreground = Color.black; //ǰ��ɫ
	public static final Color headerBackground = Color.DARK_GRAY; //����
	public static final Color headerForeground = Color.white; //����ǰ��ɫ
	public static final Color selectBackground = Color.green; //ѡ�б���ɫ
	public static final Color selectForeground = Color.white; //ѡ��ǰ��ɫ
	
	public static final String WeekSun ="������";//���ڱ�ǩ����
	public static final String WeekMon ="����һ";
	public static final String WeekTue ="���ڶ�";
	public static final String WeekWed ="������";
	public static final String WeekThu ="������";
	public static final String WeekFri ="������";
	public static final String WeekSat ="������";
	
	private JPanel MainPanel;//�������
	private JLabel yearsLabel;//��ݱ�ǩ
	private JSpinner yearsSpinner;//�����Ͽ�
	private JLabel monthsLabel;//�·ݱ�ǩ
	private JComboBox monthsComboBox;//ʮ���·�������
	private JLabel textLabel;//������ʾ��ǩ
	private JLabel InfoLabel;//������Ϣ��ʾ��ǩ
	//private JLabel daysLabel;//�ձ��
    private AbstractTableModel daysModel;//�쵥Ԫ���
    private Calendar calendar;//��������
    //=======================================================
    /*��������*/
    public Xiaorili() {         //���캯��
    	MainPanel = (JPanel) getContentPane();
    }
    
    public void init(){   //��ʼ�������溯��
    	MainPanel.setLayout(new BorderLayout());
    	calendar=Calendar.getInstance();  //Ĭ�Ϸ�ʽ���Ա���ʱ���͵���������Calendar
    	//-------------------------------------------------------------------------
    	yearsLabel=new JLabel("��ݣ�");//������ݱ�ǩ
    	yearsSpinner =new JSpinner();//�������spinner��Ͽ�
    	yearsSpinner.setEditor(new JSpinner.NumberEditor(yearsSpinner, "0000"));
    	yearsSpinner.setValue(new Integer(calendar.get(calendar.YEAR)));
    	yearsSpinner.addChangeListener(new ChangeListener(){  //ע�����Ͽ���¼�����
    		
    		public void stateChanged(ChangeEvent changeEvent){
    			int day=calendar.get(Calendar.DAY_OF_MONTH);
    			calendar.set(Calendar.DAY_OF_MONTH,1);
    			calendar.set(Calendar.YEAR, ((Integer)yearsSpinner.getValue()).intValue());
    		
    		
    		int maxDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    		calendar.set(Calendar.DAY_OF_MONTH, day>maxDay?maxDay:day);
    		updateView();  //������ʾ
    		}
    	});
    	//--------------------------------------------------------------------------------
    	JPanel yearMonthPanel =new JPanel();
    	MainPanel.add(yearMonthPanel, BorderLayout.NORTH);// ���������嵽�����������棨���Ϸ���
    	yearMonthPanel.setLayout(new BorderLayout());// �߲���ģʽ
    	JPanel yearPanel = new JPanel();// ����������
    	yearMonthPanel.add(yearPanel, BorderLayout.WEST);// ��������ӵ����������������ߣ�
    	yearPanel.setLayout(new BorderLayout());// ����������Ϊ�߲��ֲ������ݱ�ǩ����Ͽ�
    	yearPanel.add(yearsLabel, BorderLayout.WEST);
    	yearPanel.add(yearsSpinner, BorderLayout.CENTER);
    	// --------------------------------------
    	monthsLabel = new JLabel("�·�: "); // �����·ݱ�ǩ��ʾ
    	monthsComboBox = new JComboBox();// �·�������
    	for (int i = 1; i <= 12; i++) { // �����������12���·�
    	monthsComboBox.addItem(new Integer(i));
    	}
    	monthsComboBox.setSelectedIndex(calendar.get(Calendar.MONTH));// ������ǰ�·�Ϊѡ��״̬
    	monthsComboBox.addActionListener(new ActionListener() { // ע���·���������¼�������
    	public void actionPerformed(ActionEvent actionEvent) {
    	int day = calendar.get(Calendar.DAY_OF_MONTH);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	calendar.set(Calendar.MONTH, monthsComboBox
    	.getSelectedIndex());
    	int maxDay = calendar
    	.getActualMaximum(Calendar.DAY_OF_MONTH);
    	calendar.set(Calendar.DAY_OF_MONTH,
    	day > maxDay ? maxDay : day);
    	updateView();// ���������ʾ
    	}
    	});
    	// -----------------------------------------------------------------
    	JPanel monthPanel = new JPanel();// �����·����
    	yearMonthPanel.add(monthPanel, BorderLayout.EAST);// ����·���嵽�������Ķ��棨���棩
    	monthPanel.setLayout(new BorderLayout());// �·������Ϊ�߲��ַ�ʽ
    	monthPanel.add(monthsLabel, BorderLayout.WEST);// ����·����Ʊ�ǩ���·�������棨���棩
    	monthPanel.add(monthsComboBox, BorderLayout.CENTER);// ����·��������·�����м�
    	// --------------------------------------
    	textLabel = new JLabel("JAVAС����"); // ���ñ����ǩ��ʾ
    	JPanel txetPanel = new JPanel();// ��������ı���ʾ���
    	yearMonthPanel.add(txetPanel, BorderLayout.CENTER);// ��ӱ����ı���ʾ��嵽��������м�
    	txetPanel.add(textLabel, BorderLayout.CENTER);// ��ӱ����ı���ǩ�����
    	// --------------------------------------
    	InfoLabel = new JLabel("�˳���Ϣ      ϵͳ�����      ������    "); // ���ø�����Ϣ��ǩ��ʾ
    	JPanel InfoPanel = new JPanel();// ����ײ�������Ϣ��ʾ���
    	MainPanel.add(InfoPanel, BorderLayout.SOUTH);// ��Ӹ�����Ϣ��ʾ��嵽��������Ϸ����·���
    	InfoPanel.add(InfoLabel);// �����Ϣ��ǩ�ı���ǩ�����
        daysModel = new AbstractTableModel() { // ����7��7��
    	public int getRowCount() {
    	                   return 7;
                                	}
    	public int getColumnCount() {
    	                  return 7;
    	                             }
    	public Object getValueAt(int row, int column) {
    	  if (row == 0) { // ��һ����ʾ����
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
        CalendarTable daysTable = new CalendarTable(daysModel, calendar); // �����ձ��
        daysTable.setCellSelectionEnabled(true);// ���ñ��Ԫ���ѡ��
        daysTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		daysTable.setDefaultRenderer(daysTable.getColumnClass(0),
				new TableCellRenderer() {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column) {
    String text = (value == null) ? "" : value.toString();
    JLabel cell = new JLabel(text);
    cell.setOpaque(true); // ���Ʊ߽��ڵ���������
    if (row == 0) { // ��һ����ʾ���ڣ�����Ϊ���ڵ�ǰ��ɫ�ͱ���ɫ
    cell.setForeground(headerForeground);
    cell.setBackground(headerBackground);
    } else {
    if (isSelected) { // ���ڵ�Ԫ�����ѡ�У�������Ϊ����ѡ�е�ǰ���� ��ɫ
    cell.setForeground(selectForeground);
    cell.setBackground(selectBackground);
    } else { // �������ڵ�Ԫ�����ͨǰ������ɫ
    cell.setForeground(foreground);
    cell.setBackground(background);
    }
    }return cell;
    }
    });
    updateView();
    MainPanel.add(daysTable, BorderLayout.CENTER);// �������嵽��������м�
    }
    // --------------------------------------
    public static String getHeader(int index) {// ���õ�һ�����ڵ���ʾ
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
	  public void updateView() {// ���������ʾ����
	    daysModel.fireTableDataChanged();
	    
	    
	    CalendarTable daysTable = new CalendarTable(daysModel, calendar); // �����ձ��
		daysTable.setRowSelectionInterval(calendar.get(Calendar.WEEK_OF_MONTH),calendar.get(Calendar.WEEK_OF_MONTH));
	    daysTable.setColumnSelectionInterval(calendar.get(Calendar.DAY_OF_WEEK) - 1, calendar.get(Calendar.DAY_OF_WEEK) - 1);
	    }
		    
		    
		    public static class CalendarTable extends JTable {// �����
		    private Calendar calendar;
		    public CalendarTable(TableModel model, Calendar calendar) {// ���췽��
		    super(model);
		    this.calendar = calendar;	
		    }
		    
    
		    public void changeSelection(int row, int column, boolean toggle,
    boolean extend) {// ѡ����Ԫ��ʱ
    super.changeSelection(row, column, toggle, extend);
    if (row == 0) {// ѡ��Ϊ��һ�У����ڣ�ʱ���ı䵥Ԫ��
    return;
    }
    Object obj = getValueAt(row, column);
    if (obj != null) {
    calendar.set(Calendar.DAY_OF_MONTH, ((Integer) obj).intValue());
    }
    }	
		    }
	
	
	
	
			}

		/*java Calendar ������Calendar �����ඨ�����㹻�ķ������������ܹ����������Ĺ�������Ҳ����
		�Լ���Calendar ʵ���࣬Ȼ������ΪCalendar ���󷵻�(������������)����Calendar �ķ����У�
		get() ��add() ����Calendar ����ˢ�¡��������������Ƽ�������Calendar ����ʵ�֡�
		javax swing JSpinner �÷������û���һ������������ѡ��һ�����ֻ���һ������ֵ�ĵ��������ֶΡ�
		���ܹ������ѡ�����ڡ����ֻ��б��е�ѡ�Spinner ͨ���ṩһ�Դ�С��ͷ�İ�ť�Ա��𲽱�������
		Ԫ�ء����̵�����/���·����Ҳ��ѭ������Ԫ�ء�Ҳ�����û���Spinner ��ֱ������Ϸ�ֵ��JSpinner
		��ʾ��ͬ�����ݣ����ò�ͬ��ģ�ͣ���SpinnerDateModel��SpinnerListModel��SpinnerNumberModel��ֻ
		Ҫ��ģ���޸ģ��Ϳ���ʵ����Ҫ�ľ�ȷ�Ĺ��ܡ����������ø����ʵ������ݵ��޸ġ�
		setOpaque �÷���setOpaque (false)�������ǽ�button �ı���ɫ��Ϊ��parent �ı���ɫ�����Ϊ
		true��������������߽��ڵ��������ء������������ܲ�������ĳЩ���������أ��Ӷ������������
		����͸�ӳ�����
		����Java Swing �ĳ����ӱ�ǩ�ͳ����Ӱ�ť��ʵ�֣� java.awt.Desktop ���
		java.awt.Desktop.isDesktopSupported()���Ի�ȡ��ǰϵͳ�Ƿ�֧��java awt ������չ.���֧�ֿ��Ի�
		ȡ��ǰϵͳ��Desktop����ʹ�ã�
		java.awt.Desktop dp = java.awt.Desktop.getDesktop()��
		dp.browse(java.net.URI)�� �Ϳ�������ϵͳĬ�ϵ���������������Զ���һ�������ӱ�ǩ�ؼ�
		[LinkLabel]�����ʵ�ָù��ܡ�
		java ��ʵ�ֱ��Ķ��ͷ��ʾ����Ҫ��������Ӧ����ColumnGroup�� GroupableTableHeader��
		GroupableTableHeaderU ��һ��ʾ����GroupableHeaderExample��
		(1)��С�����������ṹ
		���һ�����MainFrame��Ȼ����MainFrame �����һ��MainPanel ��塣����һ��MainPanel ���
		�࣬������Ա߲��ָ�ʽ������������塢��ǩ�Ⱦ���ӵ����趨������С�
		(2) MainPanel ��山�����Ϸ��������һ���������YearMonthPanel
		�����������Ҫ�������ʾ���·���ʾ��������ʾ��YearMonthPanel ���Ҳʹ�ñ߲��ָ�ʽ��������
		��һ�������壬�������Ϣ���в����һ��TextPanel ��壬�ű����ı���ʾ��Ϣ���������һ���·�
		���MonthPanel�����·���������Ϣ��
		(3)MainPanel �������в����
		���һ��DaysPanel ������ڷ����·ݵ����������Ϣ��
		(4)MainPanel �������ϲ����·������
		���һ��InfoPanel ��壬��Ҫ���ڷ�����ʾ������Ϣ��
		(1)����༰�������
		�ڱ������У�������һ��Frame �࣬��Ҫ����һ�������ܣ�������ʾ������Ϣ��������������ֻ��
		��һ���ô������ʵ�ֳ����ܡ�
		������һ�������������Xiaorili�����а���������������õ���ȫ�ֱ��������ܺ����ȣ���Frame
		���ﶨ����������ڸ�������������������һ���ڲ���CalendarTable ����࣬���ڲ������������
		ʾ��ʽ��
		Ϊ�����һ���������ӣ�������һ��LinkLable �࣬����������һ���ļ�LinkTbale.java �У�������
		�����Xiaorili �ﶨ���������
		(2)�������˼·
		��.���������㷨��������if ����ж϶����굽������֮��ÿһ���Ƿ�Ϊ���꣬�����꣬���������
		��Ϊ366������Ϊ365��Ȼ���ж���������Ƿ�Ϊ�����꣬���ǣ���������S=1���������ۼӷ������
		�����굽������֮������������ٰ��������һ�µ�Ҫ������·�֮��������ۼ���������������������
		���·ݲ��Ҹ��»����ڶ��£���ʹ��������1�����򣬲��ӣ�������Ӷ�����һ��һ�յ������ĸ���һ��
		����������
		��.����·ݵ�һ��Ϊ���ڼ����㷨��ʹ����������7 ȡ���2 �ü���Ϊ���ڼ�������7����Ϊ�����ա�
		��.�������·ݵ�һ��Ϊ���ڼ����㷨���������·ݵ�һ��Ϊ���ڼ��󣬰Ѹ�������ǰ��λ���ÿ�
		���ϣ����ܸ�����һ���������ֱ���µף������е��������ϸ���һ��Ϊ���ڼ��������ٳ���7 ��0 ��
		�У�����������������µ�������
		��.������С��1582 ����������жϡ�
		*/
