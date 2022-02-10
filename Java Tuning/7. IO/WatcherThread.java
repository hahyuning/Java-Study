import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

public class WatcherThread extends Thread {
    String dirName;

    public WatcherThread(String dirName) {
        this.dirName = dirName;
    }

    public void run() {
        System.out.println("Watcher is started");
        fileWatcher();
        System.out.println("Watcher is ended");
    }

    public void fileWatcher() {
        try {
            // Path 객체를 생성해서 모니터링할 디렉터리를 지정한다.
            Path dir = Paths.get(dirName);
            // WatchService 클래스의 watcher 객체를 생성한다.
            WatchService watcher = FileSystems.getDefault().newWatchService();
            // Path 객체의 register() 메서드를 이용하여 파일이 생성, 수정, 삭제되는 이벤트를 처리하도록 지정한다.
            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            WatchKey key;
            for (int loop = 0; loop < 4; loop++) {
                // WatchService 객체의 take() 메서드를 호출하면 해당 디렉터리에 변경이 있을때까지 기다리다가
                // 작업이 발경되면 WatchKey 객체가 생성된다.
                key = watcher.take();
                String watchedTime = new Date().toString();

                // 파일에 변화가 생겼다면 이벤트의 목록을 가져온다.
                List<WatchEvent<?>> eventList = key.pollEvents();
                for (WatchEvent<?> event : eventList) {
                    Path name = (Path) event.context();

                    if (event.kind() == ENTRY_CREATE) {
                        System.out.format("%s created at %s%n", name, watchedTime);
                    } else if (event.kind() == ENTRY_DELETE) {
                        System.out.format("%s deleted at %s%n", name, watchedTime);
                    } else {
                        System.out.format("%s modified at %s%n", name, watchedTime);
                    }
                }
                // 이벤트를 처리한 다음 WatchKey 객체를 reset
                key.reset();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
