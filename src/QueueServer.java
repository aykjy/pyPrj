
import java.util.ArrayDeque;
public class QueueServer {
	public static void main(String[] args){
		TaskQueue thisTaskQueue=new TaskQueue();//�������
		AddTask aat = new AddTask(thisTaskQueue);//�������
		aat.start();//��������ʼ
		
		Server aServer=new Server(thisTaskQueue);
		aServer.start();
	} 	
}
//�����������
class AddTask extends Thread{
	public TaskQueue tq;
	
	public AddTask(TaskQueue tqe){
		this.tq = tqe;
	}
	
	//����һ���������������
	public  void addTask(String taskName){			
			tq.add(taskName);
			System.out.println(taskName);		
	}
	
	public void run(){
		int i=0;
		while(true){
			if(tq.size()<tq.getMaxSize()){
				i++;
				String taskName= "add a task"+i;
				addTask(taskName);
				try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}				
			}
			else
			{
				System.out.println("have reached max");
				try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}	
			}			
		}
	}
}

class TaskQueue{
	ArrayDeque<String> aTaskQueue=new ArrayDeque<>();
	private int MaxSize=10;
	private boolean flag; //��ʶ���е�״̬
		
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public int getMaxSize(){
		return this.MaxSize;
	}
	
	public int size(){
		return aTaskQueue.size();
	}
	
	public void add(String str){
		this.aTaskQueue.add(str);
	}
	
	public Object get(){
		return aTaskQueue.peek(); 		
	}
	
	public Object remove(){
		return aTaskQueue.poll();		
	}
}

class Server extends Thread{
	int status=0;//���ִ������ʱ���÷�����״̬Ϊ1������Ϊ0
	TaskQueue thisTaskQueue;

	public Server(TaskQueue aTaskQueue){
		thisTaskQueue=aTaskQueue;
	}
	
	//ִ��һ������
	public  void  serveTask(){  
		//������������ʱ��ִ����������еĵ�һ������
		//���򣬵ȴ�
		if(thisTaskQueue.size()==0){
			System.out.println("������г���Ϊ"+thisTaskQueue.size());				
		}
		else{
			if(thisTaskQueue.size()>0){
				Object aTaskName=thisTaskQueue.get();
				thisTaskQueue.remove();
				System.out.println(" performs "+ aTaskName );
			}
			else{
				System.out.println("the task is null");			
			}
		}
	}	
	
	@Override
	public void run(){
		while(true){				
				serveTask();				
				try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}	
}