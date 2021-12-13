public class WatcherMain {

	public static void main(String[] args) {

		FileWatcherThread fileWatcherThread = new FileWatcherThread();
		fileWatcherThread.start();
		try {
			fileWatcherThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
