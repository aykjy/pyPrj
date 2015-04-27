
import java.util.ArrayDeque;
public class QueueServer {
	public static void main(String[] args){
		TaskQueue thisTaskQueue=new TaskQueue();//任务队列
		AddTask aat = new AddTask("aat",thisTaskQueue);//任务产生
		AddTask bat=new AddTask("bat",thisTaskQueue);
		aat.start();//产生任务开始
		bat.start();
		
		Server aServer=new Server("aServer ",thisTaskQueue);
		Server bServer=new Server("bServer ",thisTaskQueue);
		aServer.start();
		bServer.start();
		
	} 	
}
//任务产生器类
class AddTask extends Thread{
	String name;
	public TaskQueue tq;
	
	public AddTask(String name,TaskQueue tqe){
		this.name=name;
		this.tq = tqe;
	}
	
	//增加一个任务到任务队列中
	public synchronized void addTask(String taskName){			
			tq.add(taskName);
			System.out.println(taskName);		
	}
	
	public void run(){
		int i=0;
		while(true){
			if(tq.size()<tq.getMaxSize()){
				i++;
				String taskName= this.name+"add a task"+i;
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
	private boolean flag; //标识队列的状态
		
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
	int status=0;//如果执行任务时，置服务器状态为1，否则为0
	String name;
	TaskQueue thisTaskQueue;

	public Server(String name,TaskQueue aTaskQueue){
		this.name=name;
		thisTaskQueue=aTaskQueue;
	}
	
	//执行一个任务
	public synchronized void  serveTask(){  
		//当服务器空闲时，执行任务队列中的第一个任务
		//否则，等待
		//System.out.println("Start to perform task.");
		if(thisTaskQueue.size()==0){
			System.out.println("任务队列长度为"+thisTaskQueue.size());				
		}
		else{
			if(thisTaskQueue.size()>0){
				Object aTaskName=thisTaskQueue.get();
				thisTaskQueue.remove();
				System.out.println(this.name+" performs "+ aTaskName );
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