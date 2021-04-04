import requests
from packages.exceptions import NoInputError


class Player():

    def __init__(self):
        self.username = None
        self.rank = None
        self.stars = None
        self.final_kdr = None
        self.win_lose_ratio = None
        self.bed_break_lose_ratio = None
        self.final_kills = None
        self.wins = None
        self.games_played = None

    def fetch_stats_no_api(self, uuid: str = "", username: str = ""):

        if(uuid):
            data = requests.get("https://api.slothpixel.me/api/players/" + uuid).json()
        elif(username):
            data = requests.get("https://api.slothpixel.me/api/players/" + username).json()
        else:
            raise NoInputError

        try:
            self.username = data["username"]
            self.rank = data["rank"]
            data = data["stats"]["BedWars"]
            self.stars = data["level"]
            self.final_kdr = data["final_k_d"]
            self.win_lose_ratio = data["w_l"]
            self.bed_break_lose_ratio = data["bed_ratio"]
            self.final_kills = data["final_kills"]
            self.wins = data["wins"]
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
        print("games_played -", self.games_played)
        print("\n")
