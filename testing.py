from resources.fetchStats import Player
import web.web

playerArr = []

sf = Player()
sf.fetch_stats_no_api("SquidFish")

for i in range(8):

    playerArr.append(sf)

print("Done fetching stats pog")

app = web.web.create_app(playerArr)
app.run()
