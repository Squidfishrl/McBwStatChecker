

class NoInputError(Exception):

    """
    Raised when fetch_stats_api or fetch_stats_no_api is called without
    specifiying uuid or username.
    """

    pass


class RecentLookUpError(Exception):

    """
    Raised when Hypixel API blocks viewing the stats when fetch_stats_api is
    called for the same username/uuid in short periods of time.
    """

    pass


class InvalidAPIKey(Exception):

    """
    Raised when the API key isn't valid. Type /api in hypixel and use that
    instead.
    """
