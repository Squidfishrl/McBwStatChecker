from resources.fetchStats import Player
from resources.statFile import append_obj, modify_obj, clear_file, delete_obj
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler
from concurrent.futures import ThreadPoolExecutor, as_completed
import web
import time
import os
# from Naked.toolshed.shell import run_js


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

        print("bro what")
        if event.src_path == playerLogPath:

            print(f'event type: {event.event_type}  path : {event.src_path}')

            if os.stat(playerLogPath).st_size == 0:
                # file is empty
                self.AlrReadLine = 0
                self.playerArr = []
                clear_file()

            else:

                playerLogCommands = open(playerLogPath, 'r')

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
                                    found_player = False
                                    for player in self.playerArr:
                                        if player.username == lineArr[1]:
                                            player.team_colour = colorDict[lineArr[2]]
                                            modify_obj("username", player.username, "team_colour", player.team_colour)
                                            found_player = True

                                    if found_player is False:
                                        pl = self.fetchStat(lineArr[1])
                                        pl.team_colour = colorDict[lineArr[2]]
                                        append_obj(pl)
                                        self.playerArr.append(pl)
                                    # inser colour
                                else:
                                    if lineArr[0] == "add":

                                        threads.append(executor.submit(self.fetchStat, lineArr[1]))

                                        for task in as_completed(threads):

                                            exists = False
                                            for player in self.playerArr:
                                                if player.username == task.result().username:
                                                    exists = True
                                                    break

                                            if exists is False:
                                                self.playerArr.append(task.result())
                                                # pass this to webframework
                                                print(task.result().__dict__)
                                                append_obj(task.result())
                                                print("it doesnt crash")


                                    elif lineArr[0] == "rm":
                                        print("searching for remove");
                                        for enum, player in enumerate(self.playerArr):
                                            print(player.username, line[1])
                                            if player.username == lineArr[1]:
                                                self.playerArr.pop(enum)
                                                print("removed player")
                                                delete_obj(player)

                                self.AlrReadLine += 1

                except ValueError:
                    # parsed the entire file
                    playerLogCommands = open(playerLogPath, 'r+')
                    playerLogCommands.truncate()
                    playerLogCommands.close()
                    self.AlrReadLine = 0

                playerLogCommands.close()


colorDict = {
    "R": "255, 0, 0",
    "W": "255, 255, 255",
    "S": "128, 128, 128",
    "P": "128, 0, 128",
    "B": "0, 0, 255",
    "A": "0, 255, 255",
    "Y": "255, 255, 0",
    "G": "0, 128, 0"
}

playerLogPath = "/home/ivo/.minecraft/playerNames.txt"

if __name__ == '__main__':

    event_handler = FHandler()
    observer = Observer()
    observer.schedule(event_handler, path=playerLogPath, recursive=False)
    observer.start()

    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        observer.stop()
    observer.join()
