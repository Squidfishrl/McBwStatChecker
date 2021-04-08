from setuptools import setup

setup(
    name='web',
    packages=['web'],
    include_package_data=True,
    install_requires=[
        'flask',
    ],
)


"""
 cd web
 export FLASK_APP=web
 export FLASK_ENV=development
 flask run
"""
