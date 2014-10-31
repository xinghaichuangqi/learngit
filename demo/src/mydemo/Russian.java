package mydemo;
//源代码：

//import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
 
 
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
 
public class Russian extends JFrame implements ActionListener{
        
          JMenu control=new JMenu("控制");
          JMenuItem start=new JMenuItem("开始");
          
          JMenuItem pause=new JMenuItem("暂停");
          JMenuItem restart=new JMenuItem("重来");
          
          
          
          
          JMenu explain=new JMenu("说明");
          JMenuItem help=new JMenuItem("帮助");
          JMenuItem about=new JMenuItem("关于");
          
          JMenuBar menu=new JMenuBar();
          
        
          Block2 block;
          Thread main;
          
         public Russian(){
                   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  
                    control=new JMenu("控制");
                    
                    start=new JMenuItem("开始");
                    start.addActionListener(this);
                    
                    pause=new JMenuItem("暂停");
                    pause.addActionListener(this);
                    
                   restart=new JMenuItem("重来");
                   restart.addActionListener(this);
                    
                    
                    
                    explain=new JMenu("说明");
                    
                    help=new JMenuItem("帮助");
                    help.addActionListener(
                                      new ActionListener(){
                                                public void actionPerformed(ActionEvent arg0) {
                                                         JOptionPane.showMessageDialog(null, "左右键挪动，\n空格健翻转，\n每一千分为一级","帮助",JOptionPane.INFORMATION_MESSAGE );
                                                                 // TODO Auto-generated method stub
                                                                
                                                        }
 
                                              
                                              
                                                
                                      });
                    
                   about=new JMenuItem("关于");
                    about.addActionListener(
                                      new ActionListener(){
                                                public void actionPerformed(ActionEvent arg0) {
                                                         JOptionPane.showMessageDialog(null,"Hello world !，\n ","关于",JOptionPane.INFORMATION_MESSAGE);
                                                                 // TODO Auto-generated method stub
                                                }
                                      });
                         
                  
                   menu=new JMenuBar();
                  
                  
                   block=new Block2();
                   main=new Thread(block);
                  
                   control.add(start);
                  
                   control.add(pause);
                  
                   control.add(restart);
                  
                  
                   explain.add(help);
                  
                   explain.add(about);
                  
                  
                  
                    menu.add(control);
                    menu.add(explain);
                  
                  
                   ;
                  
                  
                  
                  
                  
                  
                   add(block);
                   setJMenuBar(menu);
                   this.addKeyListener(block);
                  
                   setSize(260,300);
                  
                   setVisible(true);
                  
         }
             public static void main(String [] args){
                  
                  
                   Russian test=new Russian();
                  
         }
         @Override
              public void actionPerformed(ActionEvent e) {
                  
                  
                   if(e.getSource()==start)
                   {  if(block.getPause()==0)
                               main.start();
                            else
                                     block.getTimer().start();
                  
                   }
                   else if(e.getSource()==pause)
                   {
                            block.pause();
                   }
                   else if(e.getSource()==restart)
                   {
                   block.inite();
                   block.getTimer().restart();
                   }
                  
                  
         }
 
}
 
 
 
class Block2 extends  JPanel implements KeyListener,Runnable{
        
          ActionListener listener=new ActionListener(){
                     public void actionPerformed(ActionEvent e){
                              repaint();
                             
                              fall();
                     }
                     
             };
        
        
        
         private static final long serialVersionUID = 1L;
          int map [][] =new int[12][22];
        
         int map2[][]={{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
         int time=1000;
        
 
         private Timer timer=new Timer(time,listener);
        
         int start;
         private int pause;
        
         int stap;  //第几级
         int x=4;
         int y=1;
         long score;
         int gameover;
         int candown,cannew; //两把锁
        
         int type=0,state=0;
         int nexttype,nextstate;
        
         public Timer getTimer() {
                   return timer;
         }
 
 
 
         public void setTimer(Timer timer) {
                   this.timer = timer;
         }
 
 
 
         public int getX() {
                   return x;
         }
 
 
 
         public void setX(int x) {
                   this.x = x;
         }
 
 
 
         public int getY() {
                   return y;
         }
 
 
 
         public void setY(int y) {
                   this.y = y;
         }
         public int getPause() {
                   return pause;
         }
 
 
         public void setPause(int pause) {
                   this.pause = pause;
         }
        
         int shapes[][][]=
                           
                   {                
                            //方块
                            {
                            {0,0,0,0,  0,1,1,0,  0,1,1,0  ,0,0,0,0},
                            {0,0,0,0,  0,1,1,0,  0,1,1,0  ,0,0,0,0},
                            {0,0,0,0,  0,1,1,0,  0,1,1,0  ,0,0,0,0},
                            {0,0,0,0,  0,1,1,0,  0,1,1,0  ,0,0,0,0}
                            },
                            //长条
                            {
                            {0,0,0,0, 1,1,1,1, 0,0,0,0,  0,0,0,0},
                            {0,1,0,0, 0,1,0,0, 0,1,0,0,  0,1,0,0},
                            {0,0,0,0, 1,1,1,1, 0,0,0,0,  0,0,0,0},
                            {0,1,0,0, 0,1,0,0, 0,1,0,0,  0,1,0,0}
                            },
                            //L形
                            {
                            {0,1,0,0,  0,1,0,0,  0,1,1,0,  0,0,0,0},
                            {0,0,0,0,  0,0,1,0,  1,1,1,0,  0,0,0,0},
                            {0,1,1,0,  0,0,1,0,  0,0,1,0,  0,0,0,0},
                            {0,0,0,0,  1,1,1,0,  1,0,0,0,  0,0,0,0}
                                    
                            },
                            //反L形
                            {
                                     {0,0,1,0,  0,0,1,0,  0,1,1,0,  0,0,0,0},
                                     {0,0,0,0,  1,1,1,0,  0,0,1,0,  0,0,0,0},
                                     {0,1,1,0,  0,1,0,0,  0,1,0,0,  0,0,0,0},
                                     {0,0,0,0,  1,0,0,0,  1,1,1,0,  0,0,0,0}
                            },
                            //左高右低
                            {
                                     {0,1,0,0,  0,1,1,0,  0,0,1,0,  0,0,0,0},
                                     {0,0,0,0,  0,0,1,1,  0,1,1,0,  0,0,0,0},
                                     {0,1,0,0,  0,1,1,0,  0,0,1,0,  0,0,0,0},
                                     {0,0,0,0,  0,0,1,1,  0,1,1,0,  0,0,0,0}
                            },
                            //右高左低
                            {
                                     {0,0,1,0,  0,1,1,0,  0,1,0,0,  0,0,0,0},
                                     {0,0,0,0,  1,1,0,0,  0,1,1,0,  0,0,0,0},
                                     {0,0,1,0,  0,1,1,0,  0,1,0,0,  0,0,0,0},
                                     {0,0,0,0,  1,1,0,0,  0,1,1,0,  0,0,0,0}
                            },
                            //T形
                            {
                                     {0,0,0,0,  0,1,0,0,  1,1,1,0,  0,0,0,0},
                                     {0,0,1,0,  0,1,1,0,  0,0,1,0,  0,0,0,0},
                                     {0,0,0,0,  1,1,1,0,  0,1,0,0,  0,0,0,0},
                                     { 0,1,0,0, 0,1,1,0,  0,1,0,0,  0,0,0,0}
                            }
                           
                   };
 
         void inite(){
                  
                   start=0;
                   pause=0;
                    time=1000;
                    stap=1;
                    x=4;
                    y=1;
                  
                             score=0;
                             gameover=0;
                             candown=0;
                             cannew=1; //两把锁
                           
                             type=0;
                             state=0;
                  
                   //状态0作为边界，状态1为底板，状态2为可动，状态3固定
                   for(int i=0;i<12;i++)
                     {
                            map[i][21]=0;
                            map[i][0]=1;
                           
                     }
                  
                   for(int j=0;j<22;j++)
                   {map[0][j]=0;
                    map[11][j]=0;
                  
                   }
                   for(int a=1;a<11;a++)
                            for(int b=1;b<21;b++)
                                     map[a][b]=1;
                  
                  
                  
         }
        
 
         public void newblock(){
                   if(start==0)
                   {       
                    type=new Random().nextInt(7);            
                    state=new Random().nextInt(4);
                    start=1;
                   }
                   else{
                   type=nexttype ;
                   state=nextstate;
                   }
                    nexttype=new Random().nextInt(7);
                    nextstate=new Random().nextInt(4);
                   x=4;
                   y=1;
                  
                  
                   for(int k=0;k<16;k++)
                            if(shapes[type][state][k]==1)
                                     map[x+k%4][y+k/4]=2;
                                    
                   candown=1;
                                    
        
          
          
         }
        
        
        
         public void paint(Graphics g){
                   super.paint(g);  //清除缓存
                  
                  
                  
                  
                   for(int i=0;i<12;i++)
                     for(int j=1;j<22;j++)
                     { if(map[i][j]==0)
                       {
                       g.drawRect(20+10*i,10*j-10,10,10);
                             
                       }
                      if(map[i][j]==2||map[i][j]==3)
                              g.fillRect(20+10*i,10*j-10,10,10);
                   }        
                   if(start==1)
                     {
                            preview(g);
                           
                           
                     }
                  
      g.drawString("分数 : "+score,150, 50);
            g.drawString("第"+stap+"级", 150, 30);
        
  }
        
         public void fall(){
                  
                  
                   if(candown==1)
                            down();
                  
                   if(cannew==1)
                   {newblock();
                    cannew=0;
                   }
                  
         }

        
         public void down(){
                  
                  
                   for(int a=1;a<11;a++)
              for(int b=1;b<21;b++)
                      if(map[a][b]==2&&(map[a][b+1]==0||map[a][b+1]==3))
                      {
                     candown=0;
                      }
                   if(candown==1)
                            y++;
                  
           if(candown==0)
                   {for(int a=1;a<11;a++)
                      for(int b=1;b<21;b++)
                  
                           if(map[a][b]==2)
                           {
                                     map[a][b]=3;
                          candown=0;
                          cannew=1;
                        
                           }
                    //已经触底，判断是否有满行
                   fulllinejudge();
           for(int i=1;i<11;i++)
             for(int j=1;j<21;j++)
                    if(map[i][j]==3&&j<=2)
                      { cannew=0;
                         gameover=1;
                        
                         JOptionPane.showMessageDialog(null, "Game Over !");
                                     System.exit(0);
                                      
                       }
                  
                   }
 if(candown==1)       
         {for(int i=10;i>0;i--)
                    for(int j=20;j>0;j--)//很有意思的一个逻辑啊，如果是j递增效果则完全不同
                                                
                     {                                                                
                             if(map[i][j]==2&&(map[i][j+1]==1||map[i][j+1]==2))
                                      {   if(map[i][j-1]==3)
                                           {
                                                   map[i][j+1]=map[i][j];
                                              map[i][j]=1;
                                                 
                                             }
                                           else
                                            {
                                             map[i][j+1]=map[i][j];
                                             map[i][j]=map[i][j-1];
                                           
                                            
                                            
                                            }
                                       
                                        // y++;
                                       //为什么不能够在这里y++
                                      //  System.out.println(y);
                                         
                                       }
                           
                           
                                                
                     }
                  
                  
                    
                  
         repaint();  
         }
 }
  
         public void fulllinejudge(){
                  
                   for(int j=20;j>1;j--)
                           
                   {
                            int i=1;
                            while(map[i][j]==3)
                                     i++;
                   //      System.out.printf("at the %d line have %d block\n",j,i);
                    if(i==11)//满行
                     { 
                           
//                         for(int k=1;k<11;k++)
//                         map[k][j]=1;
                      for(int a=1;a<11;a++)
                             for(int b=j;b>1;b--)
                                      map[a][b]=map[a][b-1];
                         score=score+100;
                      
                       if(score!=0)//&&score00==0)
                                {
                                time=time-100;
                                stap++;
                                timer.setDelay(time);
                               
                                }
                    
                     
                            j++; 
                    }
                    repaint();
                   }
                  
         }
        
 public Block2(){
           inite();
          
          
          
     }

public void turn(){
        
        
         //  System.out.printf("x=%d,y=%d\n",x,y);
          
           int k=0;
           int flag=0;
           while(k<16)
           { if(shapes[type][state][k]==1)
                            if(x+k%4>=1&&x+k%4<=10&&y+k/4<=20&&map[x+k%4][y+k/4]!=3)
                                     {
                                     //System.out.println(x+k%4);
                                                                       
                                     flag++;
                                    
                                     }
            k++;
           }
          
 if(flag==4)
         {for(int i=1;i<11;i++)
                   for(int j=1;j<21;j++)
                     if(map[i][j]==2)
                     {
                                              
                             map[i][j]=1;                               
                     }
 
          for(k=0;k<16;k++)
                   if(shapes[type][state][k]==1)
                            {map[x+k%4][y+k/4]=2;
                            }
          state=(state+1)%4;
          repaint();
         }
        
        
}
 
public void keyPressed(KeyEvent e) {
         switch(e.getKeyCode())
         { case KeyEvent.VK_SPACE:
               turn();
                     break;
        
           case KeyEvent.VK_LEFT:
             tryleft();
                   break;
          
                  
                  
          case KeyEvent.VK_RIGHT :
                   tryright();
                   break;
          case KeyEvent.VK_DOWN:
                    down();
                 break;
                  
                  
        
         }
         // TODO Auto-generated method stub
        
}

public void tryleft(){
        
         int flag=1;
         for(int i=1;i<11;i++)
                   for(int j=1;j<21;j++)
                            if(map[i][j]==2&&(map[i-1][j]==0||map[i-1][j]==3))
                                     flag=0;
          if(flag==1)
          {for(int i=1;i<11;i++)
                            for(int j=1;j<21;j++)
                                     if(map[i][j]==2&&(map[i+1][j]==1||map[i+1][j]==2))
                              {
                          map[i-1][j]=map[i][j];
                           map[i][j]=map[i+1][j];
                        
                              }
                                     else if(map[i][j]==2)
                                     {
                                               map[i-1][j]=map[i][j];
                                                  map[i][j]=1;
                                                
                                     }
              x--;
            repaint();            
          }
                  
        
}
public void tryright(){
         int flag=1;
         for(int i=1;i<11;i++)
                   for(int j=1;j<21;j++)
                            if(map[i][j]==2&&(map[i+1][j]==0||map[i+1][j]==3))
                                     flag=0;
          if(flag==1)
          {for(int i=10;i>0;i--)
                            for(int j=1;j<21;j++)
                                     if(map[i][j]==2&&(map[i-1][j]==1||map[i-1][j]==2))
                            {
                      map[i+1][j]=map[i][j];
                      map[i][j]=map[i-1][j];
                    
                            }
                                     else if(map[i][j]==2)
                                     {
                                               map[i+1][j]=map[i][j];
                                                  map[i][j]=1;
                                                 
                                     }
            x++;
            repaint();
          }
        
}
public void preview(Graphics g){
        
          for(int i=0;i<4;i++)
                    for(int j=0;j<4;j++)
                             map2[i][j]=0;
        
         for(int k=0;k<16;k++)
                   if(shapes[nexttype][nextstate][k]==1)
                            map2[k%4][k/4]=1;
         for(int i=0;i<4;i++)
                   for(int j=0;j<4;j++)
                            if(map2[i][j]==1)
                                     g.fillRect(160+10*i,70+10*j,10,10);
}
 
@Override
public void keyReleased(KeyEvent arg0) {
         // TODO Auto-generated method stub
        
}

@Override
public void keyTyped(KeyEvent arg0) {
        
        
        
        
}

@Override
public void run() {
        
//               for(int i=0; ;i++)       
//         System.out.println("hello world !");          
                      
                               timer.start();
                                 
         }
         // TODO Auto-generated method stub
 
public void pause(){
         pause=1;
         timer.stop();
}

}