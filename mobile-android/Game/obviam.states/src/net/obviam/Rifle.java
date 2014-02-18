/**
 * 
 */
package net.obviam;

/**
 * @author impaler
 *
 */
public class Rifle {

	// the states of the rifle
	RifleState emptyState;
	RifleState autoFireState;
	RifleState manualFireState;
	RifleState outOfAmmoState;
	RifleState roundFiredState;
	RifleState ammoFiredState;
	
	RifleState state = emptyState;
	int ammoCount = 0;
	
	// constructor
	public Rifle() {
		// creating states
		this.emptyState 		= new NoClipState(this);
		this.autoFireState 		= new AutoFireState(this);
		this.manualFireState	= new ManualFireState(this);
		this.outOfAmmoState		= new OutOfAmmoState(this);
		this.roundFiredState	= new RoundFiredState(this);
		this.ammoFiredState		= new AmmoFiredState(this);
		
		this.state 		= this.emptyState;
		this.ammoCount 	= 0;
	}
	// convenience methods
	public void insertClip() {
		this.state.insertClip();
	}
	public void ejectClip() {
		this.state.ejectClip();
	}
	public void switchManualAuto() {
		this.state.switchManualAuto();
	}
	public void pullTrigger() {
		this.state.pullTrigger();
	}
	
	// getters and setters
	public RifleState getEmptyState() {
		return emptyState;
	}
	public void setEmptyState(RifleState emptyState) {
		this.emptyState = emptyState;
	}
	public RifleState getAutoFireState() {
		return autoFireState;
	}
	public void setAutoFireState(RifleState autoFireState) {
		this.autoFireState = autoFireState;
	}
	public RifleState getManualFireState() {
		return manualFireState;
	}
	public void setManualFireState(RifleState manualFireState) {
		this.manualFireState = manualFireState;
	}
	public RifleState getOutOfAmmoState() {
		return outOfAmmoState;
	}
	public void setOutOfAmmoState(RifleState outOfAmmoState) {
		this.outOfAmmoState = outOfAmmoState;
	}
	public RifleState getRoundFiredState() {
		return roundFiredState;
	}
	public void setRoundFiredState(RifleState roundFiredState) {
		this.roundFiredState = roundFiredState;
	}
	public RifleState getAmmoFiredState() {
		return ammoFiredState;
	}
	public void setAmmoFiredState(RifleState ammoFiredState) {
		this.ammoFiredState = ammoFiredState;
	}
	public RifleState getState() {
		return state;
	}
	public void setState(RifleState state) {
		this.state = state;
	}
	public int getAmmoCount() {
		return ammoCount;
	}
	public void setAmmoCount(int ammoCount) {
		this.ammoCount = ammoCount;
	}
	
}
