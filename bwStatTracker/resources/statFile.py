import json


def append_obj(obj):

    obj = obj.__dict__
    with open('playerStats.json', 'a') as f:
        json.dump(obj, f, sort_keys=True, indent=4)


def clear_file():

    with open('playerStats.json', 'w') as f:
        json.dump("", f)
