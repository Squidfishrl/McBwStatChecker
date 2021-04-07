from resources.fetchStats import Player
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler
import time
import os


class FHandler(FileSystemEventHandler):

    def __init__(self):
        self.AlrReadLine = 0

    def on_modified(self, event):
        # method has to be called on_modified else watchdogs doesnt send events
        print(f'event type: {event.event_type}  path : {event.src_path}')

        if os.stat("/home/ivo/.minecraft/playerNames.txt").st_size == 0:
            # file is empty
            self.AlrReadLine = 0
        else:

            playerLogCommands = open("/home/ivo/.minecraft/playerNames.txt", 'r')

            for lineCount, line in enumerate(playerLogCommands):

                if lineCount < self.AlrReadLine:
                    # i've already read this line
                    continue
                elif lineCount == self.AlrReadLine:
                    # it's my first time reading this line

                    # process line:
                    lineArr = str.split(line)

                    if lineArr[0] == "add":
                        player = Player()
                        player.fetch_stats_no_api(lineArr[1])
                        player.print_stats()
                        
                    self.AlrReadLine += 1

            playerLogCommands.close()


if __name__ == '__main__':

    event_handler = FHandler()
    observer = Observer()
    playerLogPath = "/home/ivo/.minecraft/playerNames.txt"
    observer.schedule(event_handler, path=playerLogPath, recursive=False)
    observer.start()

    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        observer.stop()
    observer.join()
