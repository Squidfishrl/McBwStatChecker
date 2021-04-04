

class NoInputError(Exception):
    """
    Raised when fetch_stats_api or fetch_stats_no_api is called without
    specifiying uuid or username
    """
    pass
