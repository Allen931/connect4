package connect4.GUI;

public class Time {
    private long begin;
    private long end;

    public Time(long time) {
        begin = time;
        end = time;
    }

    public void update(long time) {
        end = time;
    }

    public void reset(long time) {
        begin = time;
        end = time;
    }

    public long getTime() {
        return (end - begin) / 1000;
    }

    @Override
    public String toString() {
        return String.format("%02d: %02d", Math.floorDiv(getTime(), 60), getTime() % 60);
    }
}
