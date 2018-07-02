package clips;

import CLIPSJNI.Router;

public class ClipsRouter extends Router {

	public ClipsRouter() {
		super("router");
	}
	
	@Override
	public void print(String arg0, String arg1) 
	{
		System.out.print(arg1);
		/*if(arg0.equals("wtrace"))
		{
			tf.getChildren().add(new Text(arg1));
		}else{ 
			tf2.getChildren().add(new Text(arg1));
		}*/	
	//	super.print(arg0, arg1);
	}

}
