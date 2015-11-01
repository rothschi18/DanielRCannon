package cs301.cannon;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;



/**
 * CannonMainActivity
 * 
 * This is the activity for the cannon animation. It creates a AnimationCanvas
 * containing a particular Animator object
 * 
 * @author Andrew Nuxoll
 * @version September 2012
 * 
 */
public class CannonMainActivity extends Activity {

	public Animator cannon;
	public static int score;

	public TextView scoreText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cannon_main);

		// Create an animation canvas and place it in the main layout
		cannon = new Cannon();
		AnimationCanvas myCanvas = new AnimationCanvas(this, cannon);
		LinearLayout mainLayout = (LinearLayout) this.findViewById(R.id.topLevelLayout);
		mainLayout.addView(myCanvas);







	}
	/**
	 * This method will fire a shot at the designated angle and velocity
	 */

	public void fire(View view)
	{
		EditText velocityNum =(EditText) this.findViewById(R.id.velocity);
		EditText thetaNum =(EditText) this.findViewById(R.id.theta);
		EditText gravityNum =(EditText) this.findViewById(R.id.gravity);
		double velocity = Double.parseDouble(velocityNum.getText().toString());
		double gravity = Double.parseDouble(gravityNum.getText().toString());
		double theta = Double.parseDouble(thetaNum.getText().toString());
		//if the user inputs invalid data reset to 45 degrees, 100 for velocity, and 1.962 for gravity
		if(theta<0 || theta > 90)
			theta = 45;

		if(gravity<0)
			gravity = 1.962;

		if(velocity < 0)
			velocity = 100;

		cannon.reloadAndFire(velocity, theta*3.1415/180, gravity);

	}

	public void reset(View view)
	{
			cannon.reset();
	}



	/**
	 * This is the default behavior (empty menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cannon_main, menu);
		return true;
	}
}
