import json
import os


def delete_obj(obj):

    data = json.load(open(fileDir))

    for i in range(len(data)):
        if data[i]["username"] == obj.username:
            data.pop(i)
            break

    open(fileDir, "w").write(
        json.dumps(data, sort_keys=True, indent=4)
    )

def append_obj(obj):

    with open(fileDir, 'r+') as f:

        if os.stat(fileDir).st_size == 0:

            data = []
            data.append(obj.__dict__)
        else:
            data = json.load(f)
            data.append(obj.__dict__)

        f.seek(0)
        json.dump(data, f, sort_keys=True, indent=4)


def modify_obj(identifierKey, identifierValue, key, newValue):

    with open(fileDir, 'r+') as f:
        data = json.load(f)
        for i in data:
            if i[identifierKey] == identifierValue:
                i[key] = newValue
                break

        f.seek(0)
        json.dump(data, f, indent=4)
        # data = json.load(f)
        # print(data)
        # print(data[0])


def clear_file():

    with open(fileDir, 'w') as f:
        pass


fileDir = 'web/web/static/js/playerStats.json'
