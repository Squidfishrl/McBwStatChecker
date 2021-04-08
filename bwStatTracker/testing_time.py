from resources.fetchStats import Player
import time
from concurrent.futures import ThreadPoolExecutor

"""
Slothpixel api  1 thread - about 18 seconds on average

limit - 60 calls per minute
limit - 5000 calls per month

"""
startTime = time.time()

playerLogCommands = open("/home/ivo/.minecraft/playerNames.txt", 'r')

for lineCount, line in enumerate(playerLogCommands):

    lineArr = str.split(line)

    if lineArr[0] == "add":
        player = Player()
        player.fetch_stats_no_api(lineArr[1])
        player.print_stats()


playerLogCommands.close()
# about 18 seconds
print("--- %s seconds ---" % (time.time() - startTime))


"""
Hypixel api - about 11 seconds on average

limit - cant call the same person twice within a minute with the same api

"""

startTime = time.time()

playerLogCommands = open("/home/ivo/.minecraft/playerNames.txt", 'r')

for lineCount, line in enumerate(playerLogCommands):

    lineArr = str.split(line)

    if lineArr[0] == "add":
        player = Player()
        player.fetch_stats_api("bad4fc40-d880-4974-8eec-43a7ba62d385", lineArr[1])
        player.print_stats()


playerLogCommands.close()

# about 11 seconds
print("--- %s seconds ---" % (time.time() - startTime))

"""
Slothpixel api 16 threads - about 1 second on average

limit - 60 calls per minute
limit - 5000 calls per month
"""

def printAndFetch(player, username):
    player = Player()
    player.fetch_stats_no_api(username)
    player.print_stats()


startTime = time.time()

threads = []
with ThreadPoolExecutor(max_workers=16) as executor:

    playerLogCommands = open("/home/ivo/.minecraft/playerNames.txt", 'r')

    for lineCount, line in enumerate(playerLogCommands):

        lineArr = str.split(line)

        if lineArr[0] == "add":
            player = Player()
            threads.append(executor.submit(printAndFetch, player, lineArr[1]))

    playerLogCommands.close()


print("--- %s seconds ---" % (time.time() - startTime))


"""
If hypixel api is 40% faster than slothpixel, then it wouldnt even matter which one I use
"""
