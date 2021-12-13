# 멀티스레드와 NIO WatchService 학습

## 프로그램 요구사항 정의
### 메인 스레드 : WatcherMain
filewatcher 스레드의 종료를 기다렸다가, filewatcher가 종료되고 난 후 

### filewatcher 스레드 : FileWatcherThread
디렉터리 내 파일 변경을 통지받아서 변경된 파일의 이름을 출력하고 스레드를 끝내는 프로그램 작성
