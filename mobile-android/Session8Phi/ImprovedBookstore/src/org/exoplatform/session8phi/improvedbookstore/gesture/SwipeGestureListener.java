package org.exoplatform.session8phi.improvedbookstore.gesture;

import org.exoplatform.session8phi.improvedbookstore.SwipableView;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.Scroller;

public class SwipeGestureListener extends SimpleOnGestureListener {

	private SwipableView swipeView;
	private Context context;
	private Scroller scroll;
	private Handler handler;
	private int lastX=0; // the last x position during gesture, also the beginning x position of the animation
	private int direction=0; // -1 is left to right // 1 is right to left
	private boolean isAnimating=false;
	private int totalSwipeDistance=0; // the distance of the swipe gesture
	private static final int SWIPE_MIN_DISTANCE = 50; // threshold under which the animation does not start
	
	public SwipeGestureListener() {
		super();
	}
	
	public SwipeGestureListener(SwipableView v) {
		context = v.getContext();
		swipeView = v;
		handler = new Handler();
		scroll = new Scroller(context);
	}
	
	private void animate(int animationDistance) {
		isAnimating = true; // when true, no other animation can start
		String dir = (direction==1) ? "left" : "right";
		Log.d("EXO_TAG", "Moving "+dir+" on "+animationDistance+" px");
		// start scrolling automatically
		scroll.startScroll(lastX, 0, animationDistance, 0, 1000);
		// the thread that will handle the animation
		// cf http://developer.android.com/guide/components/processes-and-threads.html#WorkerThreads
		new Thread(new Runnable() {
			public void run() {
				// calculate new position
				boolean more = scroll.computeScrollOffset();
				int currX = scroll.getCurrX();
				final int diff = lastX - currX;
				// move subviews to new position
				swipeView.post(new Runnable() {
					public void run() {
						swipeView.getDetailsView().offsetLeftAndRight(diff);
						swipeView.getFullDescView().offsetLeftAndRight(diff);
						// redraw the UI
						swipeView.invalidate();
					}
				});
				if (more) { // animation is not yet finished
					lastX = currX;
					// call the thread after 16ms
					handler.postDelayed(this, 16);
				} else { // animation finished, we update the state and reinit variables
					isAnimating=false;
					totalSwipeDistance=0;
					if (direction==1)
						lastX=swipeView.getDetailsView().getWidth();
					else if (direction==-1)
						lastX=0;
					Log.d("EXO_TAG", "* lastX:"+lastX+"\n* direction:"+direction);
				}
			}
		}).start();
	}
	
	public boolean handleBackButtonPressed() {
		if (!isAnimating) {
			// if the full view is displayed, pressing the back button will animate back to normal detail view
			if (lastX==swipeView.getDetailsView().getWidth()) {
				direction = -1;
				animate(-(swipeView.getDetailsView().getWidth()));
				// false so the back action stops here
				return false;
			}
		}
		// otherwise we keep the normal behavior of the back button
		return true;
	}
	
	public boolean handleScrollFinished() {
		if (!isAnimating) {
			// distance is a positive value
			if (totalSwipeDistance<0)totalSwipeDistance=-totalSwipeDistance;
			Log.d("EXO_TAG", "Gesture ends at "+totalSwipeDistance+" px");
			    // swiped distance must be above the threshold
			if (totalSwipeDistance>=SWIPE_MIN_DISTANCE &&
					(      // AND it must lead to a correct state
							(lastX==0 && direction==1) || // from desc view to full view OR
							(lastX==swipeView.getDetailsView().getWidth() && direction==-1) // from full to desc view
					)
			){
				// calculate the remaining distance to scroll automatically
				int animationDistance = (swipeView.getDetailsView().getWidth()-totalSwipeDistance)*direction;
				animate(animationDistance);
			} else if (totalSwipeDistance>0) { // return to position before dragging
				int animationDistance = totalSwipeDistance*(-direction);
				direction = -direction;
				animate(animationDistance);
			}
		}
		return true;
	}
	
	public boolean onScroll(MotionEvent originalEvent, MotionEvent currentEvent, float distanceX, float distanceY) {
		if (!isAnimating) {
			// calculate the x position of the gesture
			final float density = context.getResources().getDisplayMetrics().density;
			int dX = (int)(distanceX*density);
			// move the views to this position
			swipeView.getDetailsView().offsetLeftAndRight(-dX);
			swipeView.getFullDescView().offsetLeftAndRight(-dX);
			// redraw the UI
			swipeView.invalidate();
			// calculate the swipe distance
			totalSwipeDistance+=dX;
			direction = (originalEvent.getX()>currentEvent.getX()) ? 1 : -1;
		}
		return true;
	}
}
