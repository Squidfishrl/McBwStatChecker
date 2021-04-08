from resources.fetchStats import Player
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler
from concurrent.futures import ThreadPoolExecutor, as_completed
import web.web
import time
import os


class FHandler(FileSystemEventHandler):

    def __init__(self):
        self.AlrReadLine = 0
        self.playerArr = []


    def fetchStats(self, username):
        player = Player()
        player.fetch_stats_no_api(username)
        return player

    def renderPage(self, data):
        app = web.web.create_app(data)
        app.run()

    def on_modified(self, event):
        # method has to be called on_modified else watchdogs doesnt send events
        print(f'event type: {event.event_type}  path : {event.src_path}')

        if os.stat("/home/ivo/.minecraft/playerNames.txt").st_size == 0:
            # file is empty
            self.AlrReadLine = 0
            self.playerArr = []
        else:

            playerLogCommands = open("/home/ivo/.minecraft/playerNames.txt", 'r')

            try:
                threads = []
                with ThreadPoolExecutor(max_workers=16-self.AlrReadLine) as executor:

                    for lineCount, line in enumerate(playerLogCommands):
                        if lineCount < self.AlrReadLine:
                            # i've already read this line
                            continue
                        else:
                            # it's my first time reading this line

                            # process line:

                            lineArr = str.split(line)
                            print(line)
                            if lineArr[0] == "add":
                                threads.append(executor.submit(self.fetchStats, lineArr[1]))

                                for task in as_completed(threads):
                                    self.playerArr.append(task.result())
                                    # pass this to webframework and go from tehre

                            self.AlrReadLine += 1


            except ValueError:
                # parsed the entire file
                playerLogCommands = open("/home/ivo/.minecraft/playerNames.txt", 'r+')
                playerLogCommands.truncate()
                playerLogCommands.close()
                self.AlrReadLine = 0

            playerLogCommands.close()

            if self.AlrReadLine == 16:
                self.renderPage(self.playerArr)

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
