# tell python that this is a package

import os
from flask import Flask
from flask import render_template


def create_app(data="", test_config=None):
    # create and configure the app
    app = Flask(__name__, instance_relative_config=True)
    app.config.from_mapping(
        SECRET_KEY='dev',
    )

    if test_config is None:
        app.config.from_pyfile('config.py', silent=True)
    else:
        app.config.from_mapping(test_config)


    try:
        os.makedirs(app.instance_path)
    except OSError:
        pass

    @app.route('/')
    def render():
        return render_template('game_stats.html', data=data)

    return app
