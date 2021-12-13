import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class FileWatcherThread extends Thread {
	private static final String WATCH_PATH="watchdir";

	@Override
	public void run() {
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			Path path = Paths.get(WATCH_PATH);
			setWatchKinds(path,watchService);
			while (true) {
				WatchKey watchKey = null;
				try {
					watchKey = watchService.take(); // 다음 변경이 감지될때까지 blocking
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				showModifiedFiles(watchKey.pollEvents()); // 변경대상에 발생한 이벤트를 가져와 파일명 출력
				resetWatchKey(watchKey,watchService); // 다음 변경감지를 위해 watchkey 리셋

				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void resetWatchKey(WatchKey watchKey, WatchService watchService) {
		if (!watchKey.reset()) {
			try {
				watchService.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void showModifiedFiles(List<WatchEvent<?>> events) {
		for (WatchEvent<?> event : events) {
			Path paths = (Path)event.context();
			System.out.println(paths.getFileName());
		}
	}

	private void setWatchKinds(Path path, WatchService watchService) throws IOException {
		path.register(watchService,
			StandardWatchEventKinds.ENTRY_CREATE,
			StandardWatchEventKinds.ENTRY_DELETE,
			StandardWatchEventKinds.ENTRY_MODIFY);
	}
}
