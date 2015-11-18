

public class Clock {
	
	private static final long MUCH = 5;
	
	private long start, end;
	
	public Clock(){
		start();
	}

	public void start() {
		this.start = System.nanoTime();
	}
	
	public void stop() {
		this.end = System.nanoTime();
	}
	
	private long getTime() {
		return end - start;
	}

	public int getMillis() {
		return (int)(getTime()/1000000);
	}
	
	public boolean tookMuchLongerThan(Clock c2) {
		return getTime() > MUCH*c2.getTime();
	}

	@Override
	public String toString(){
		if (end == 0){
			return "Clock";
		} else {
			long diff = getTime();
			if (diff > 1000000){
				diff /= 1000000;
				return diff + " ms";
			} else {
				return diff + " ns";
			}
		}
	}

}

