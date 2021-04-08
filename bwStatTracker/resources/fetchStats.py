import requests
from .exceptions import NoInputError, RecentLookUpError, InvalidAPIKey


class Player():

    def __init__(self):
        self.username = None
        self.uuid = None
        self.rank = None
        self.stars = None
        self.final_kdr = None
        self.win_lose_ratio = None
        self.bed_break_lose_ratio = None
        self.final_kills = None
        self.wins = None
        self.games_played = None
        self.winstreak = None
        self.team_colour = None

    def fetch_stats_api(self, api, uuid: str = "", username: str = ""):

        if(uuid):
            data = requests.get("https://api.hypixel.net/player?key=" + api + "&name=" + uuid).json()
        elif(username):
            data = requests.get("https://api.hypixel.net/player?key=" + api + "&name=" + username).json()
        else:
            raise NoInputError

        if data["success"] is True:

            if data["player"] is not None:
                self.username = data["player"]["playername"]
                self.uuid = data["player"]["uuid"]

                if "rank" in data["player"] and data["player"]["rank"] != "NORMAL":
                    self.rank = data["player"]["rank"]
                elif "newPackageRank" in data["player"]:
                    self.rank = data["player"]["newPackageRank"]
                elif "packageRank" in data["player"]:
                    self.rank = data["player"]["packageRank"]
                else:
                    self.rank = "Non-Donor"

                self.stars = data["player"]["achievements"]["bedwars_level"]
                data = data["player"]["stats"]["Bedwars"]
                self.final_kills = data["final_kills_bedwars"]
                self.final_kdr = self.final_kills / data["final_deaths_bedwars"]
                self.wins = data["wins_bedwars"]
                self.winstreak = data["winstreak"]
                self.win_lose_ratio = self.wins/data["losses_bedwars"]
                self.games_played = data["games_played_bedwars"]
                self.bed_break_lose_ratio = data["beds_broken_bedwars"]/data["beds_lost_bedwars"]

            elif data["player"] is None:
                self.username = username
                self.rank = "NICK"

        else:
            if data["cause"] == "You have already looked up this name recently":
                raise RecentLookUpError

            if data["cause"] == "Invalid API key":
                raise InvalidAPIKey




    def fetch_stats_no_api(self, uuid: str = "", username: str = ""):

        if(uuid):
            data = requests.get("https://api.slothpixel.me/api/players/" + uuid).json()
        elif(username):
            data = requests.get("https://api.slothpixel.me/api/players/" + username).json()
        else:
            raise NoInputError

        try:
            self.username = data["username"]
            self.uuid = data["uuid"]
            self.rank = data["rank"]
            data = data["stats"]["BedWars"]
            self.stars = data["level"]
            self.final_kdr = data["final_k_d"]
            self.win_lose_ratio = data["w_l"]
            self.bed_break_lose_ratio = data["bed_ratio"]
            self.final_kills = data["final_kills"]
            self.wins = data["wins"]
            self.winstreak = data["winstreak"]
            self.games_played = data["games_played"]
        except KeyError:
            self.username = username
            self.rank = "NICK"

    def print_stats(self):
        print("USERNAME - ", self.username)
        print("RANK - ", self.rank)
        print("stars - ", self.stars)
        print("final_kdr - ", self.final_kdr)
        print("win_lose_ratio - ", self.win_lose_ratio)
        print("bed_break_lose_ratio - ", self.bed_break_lose_ratio)
        print("final_kills - ", self.final_kills)
        print("wins - ", self.wins)
        print("winstreak = ", self.winstreak)
        print("games_played -", self.games_played)
        print("\n")

#
# gb80 = Player()
# gb80.fetch_stats_api("MY_API", username="Aspas_")
# gb80.print_stats()
