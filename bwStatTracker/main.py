from resources.fetchStats import Player
from resources.statFile import append_obj
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler
from concurrent.futures import ThreadPoolExecutor, as_completed
import web
import time
import os
from Naked.toolshed.shell import run_js


class FHandler(FileSystemEventHandler):

    def __init__(self):
        self.AlrReadLine = 0
        self.playerArr = []
        # self.app = web.create_app("")
        # self.app.run()

    def fetchStat(self, uname):
        player = Player()
        player.fetch_stats_no_api(uname)
        print("successfull fetch! ")
        return player

    def on_modified(self, event):
        # method has to be called on_modified else watchdogs doesnt send events

        if event.src_path == "/home/ivo/.minecraft/playerNames.txt":

            print(f'event type: {event.event_type}  path : {event.src_path}')

            if os.stat("/home/ivo/.minecraft/playerNames.txt").st_size == 0:
                # file is empty
                self.AlrReadLine = 0
                self.playerArr = []
            else:

                playerLogCommands = open("/home/ivo/.minecraft/playerNames.txt", 'r')

                try:
                    threads = []
                    with ThreadPoolExecutor(max_workers=16) as executor:

                        for lineCount, line in enumerate(playerLogCommands):
                            if lineCount < self.AlrReadLine:
                                # i've already read this line
                                continue
                            else:
                                # it's my first time reading this line

                                # process line:

                                lineArr = str.split(line)
                                print(line)

                                if(len(lineArr) == 3):
                                    # find match
                                    for player in self.playerArr:
                                        if player.username == line[1]:
                                            player.team_color = colorDict[lineArr[2]]
                                        else:
                                            pl = self.fetchStat(line[1])
                                            pl.team_colour = colorDict[lineArr[2]]
                                            self.PlayerArr.append(pl)
                                    # inser colour
                                else:
                                    if lineArr[0] == "add":
                                        threads.append(executor.submit(self.fetchStat, username=lineArr[1]))

                                        for task in as_completed(threads):

                                            exists = False
                                            for player in self.playerArr:
                                                if player.username == task.result().username:
                                                    exists = True
                                                    break

                                            if exists is False:
                                                self.playerArr.append(task.result())
                                                # pass this to webframework
                                                append_obj(task.result())
                                                print(task.result().__dict__)

                                    elif lineArr[0] == "rm":
                                        for enum, player in enumerate(self.playerArr):
                                            if player.username == line[1]:
                                                self.playerArr.pop(enum)

                                self.AlrReadLine += 1

                except ValueError:
                    # parsed the entire file
                    playerLogCommands = open("/home/ivo/.minecraft/playerNames.txt", 'r+')
                    playerLogCommands.truncate()
                    playerLogCommands.close()
                    self.AlrReadLine = 0

                playerLogCommands.close()


colorDict = {
    "R": "120, 0, 0",
    "W": "255, 255, 255",
    "S": "128, 128, 128",
    "P": "255, 0, 255",
    "B": "0, 0, 255",
    "A": "0, 255, 255",
    "Y": "255, 255, 0",
    "G": "0, 128, 0"
}


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
