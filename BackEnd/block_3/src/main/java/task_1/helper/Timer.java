package task_1.helper;

public class Timer {
    private long startTime;

    public static Timer start(){
        Timer timer = new Timer();
        timer.startTime = System.nanoTime();
        return timer;
    }

    public String stop(){
        double elapsedTime = System.nanoTime() - startTime;
        return String.format("%.1fs", elapsedTime/1_000_000_000);
    }

    private Timer() {
    }
}

