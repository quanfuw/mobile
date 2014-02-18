/**
 * 
 */
package net.obviam;

/**
 * @author impaler
 *
 */
public class NoClipState implements RifleState {
	
	private Rifle rifle;
	public NoClipState(Rifle rifle) {
		this.rifle = rifle;
	}

	@Override
	public void ejectClip() {
		System.out.println("!* The magazine is empty.");
	}

	@Override
	public void fireAmmo() {
		System.out.println("!* You can't fire with an empty magazine.");
	}

	@Override
	public void insertClip() {
		rifle.ammoCount = 50;
		rifle.setState(rifle.getManualFireState());
	}

	@Override
	public void pullTrigger() {
		System.out.println("!* You can't fire with an empty magazine.");
	}

	@Override
	public void switchManualAuto() {
		System.out.println("!* You can't switch with an empty magazine.");
	}

}
