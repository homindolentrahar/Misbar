package com.homindolentrahar.misbar.others.constants

import com.homindolentrahar.misbar.data.sources.local.entities.FavoritesEntity
import com.homindolentrahar.misbar.data.sources.local.entities.FavoritesWithGenres
import com.homindolentrahar.misbar.data.sources.local.entities.GenresEntity
import com.homindolentrahar.misbar.data.sources.local.entities.GenresEntity.Companion.toModel
import com.homindolentrahar.misbar.data.sources.remote.dtos.*
import com.homindolentrahar.misbar.domain.models.*
import com.homindolentrahar.misbar.utils.mappers.StringMapper

object DummyData {
    //    Movies
    fun generateMoviesDtoResponse(): MoviesDtoResponse =
        MoviesDtoResponse(page = 1, generateListMoviesDto())

    private fun generateListMoviesDto(): List<MoviesDto> = listOf(
        MoviesDto(
            overview = "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
            releaseDate = "2021-04-07",
            voteAverage = 7.6,
            id = 460465,
            title = "Mortal Kombat",
            genreIds = listOf(28, 14, 12),
            posterPath = "/nkayOAUBUu4mMvyNf9iHSUiPjF1.jpg"
        ),
        MoviesDto(
            overview = "A young boy finds himself pursued by two assassins in the Montana wilderness with a survival expert determined to protecting him - and a forest fire threatening to consume them all.",
            releaseDate = "2021-05-05",
            voteAverage = 7.2,
            id = 578701,
            title = "Those Who Wish Me Dead",
            genreIds = listOf(53, 18, 28, 9648),
            posterPath = "/xCEg6KowNISWvMh8GvPSxtdf9TO.jpg",
        ),
        MoviesDto(
            overview = "In a time when monsters walk the Earth, humanity’s fight for its future sets Godzilla and Kong on a collision course that will see the two most powerful forces of nature on the planet collide in a spectacular battle for the ages.",
            releaseDate = "2021-03-24",
            voteAverage = 8.1,
            id = 399566,
            title = "Godzilla vs. Kong",
            genreIds = listOf(878, 28, 18),
            posterPath = "/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
        ),
        MoviesDto(
            overview = "Alice, a young hearing-impaired girl who, after a supposed visitation from the Virgin Mary, is inexplicably able to hear, speak and heal the sick. As word spreads and people from near and far flock to witness her miracles, a disgraced journalist hoping to revive his career visits the small New England town to investigate. When terrifying events begin to happen all around, he starts to question if these phenomena are the works of the Virgin Mary or something much more sinister.",
            releaseDate = "2021-03-31",
            voteAverage = 5.6,
            id = 632357,
            title = "The Unholy",
            genreIds = listOf(27),
            posterPath = "/b4gYVcl8pParX8AjkN90iQrWrWO.jpg",
        ),
        MoviesDto(
            overview = "Hutch Mansell, a suburban dad, overlooked husband, nothing neighbor — a \\\"nobody.\\\" When two thieves break into his home one night, Hutch's unknown long-simmering rage is ignited and propels him on a brutal path that will uncover dark secrets he fought to leave behind.",
            releaseDate = "2021-03-26",
            voteAverage = 8.4,
            id = 615457,
            title = "Nobody",
            genreIds = listOf(28, 53, 80, 35),
            posterPath = "/oBgWY00bEFeZ9N25wWVyuQddbAo.jpg",
        ),
        MoviesDto(
            overview = "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
            releaseDate = "2021-04-07",
            voteAverage = 7.6,
            id = 460465,
            title = "Mortal Kombat",
            genreIds = listOf(28, 14, 12),
            posterPath = "/nkayOAUBUu4mMvyNf9iHSUiPjF1.jpg"
        ),
        MoviesDto(
            overview = "A young boy finds himself pursued by two assassins in the Montana wilderness with a survival expert determined to protecting him - and a forest fire threatening to consume them all.",
            releaseDate = "2021-05-05",
            voteAverage = 7.2,
            id = 578701,
            title = "Those Who Wish Me Dead",
            genreIds = listOf(53, 18, 28, 9648),
            posterPath = "/xCEg6KowNISWvMh8GvPSxtdf9TO.jpg",
        ),
        MoviesDto(
            overview = "In a time when monsters walk the Earth, humanity’s fight for its future sets Godzilla and Kong on a collision course that will see the two most powerful forces of nature on the planet collide in a spectacular battle for the ages.",
            releaseDate = "2021-03-24",
            voteAverage = 8.1,
            id = 399566,
            title = "Godzilla vs. Kong",
            genreIds = listOf(878, 28, 18),
            posterPath = "/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
        ),
        MoviesDto(
            overview = "Alice, a young hearing-impaired girl who, after a supposed visitation from the Virgin Mary, is inexplicably able to hear, speak and heal the sick. As word spreads and people from near and far flock to witness her miracles, a disgraced journalist hoping to revive his career visits the small New England town to investigate. When terrifying events begin to happen all around, he starts to question if these phenomena are the works of the Virgin Mary or something much more sinister.",
            releaseDate = "2021-03-31",
            voteAverage = 5.6,
            id = 632357,
            title = "The Unholy",
            genreIds = listOf(27),
            posterPath = "/b4gYVcl8pParX8AjkN90iQrWrWO.jpg",
        ),
        MoviesDto(
            overview = "Hutch Mansell, a suburban dad, overlooked husband, nothing neighbor — a \\\"nobody.\\\" When two thieves break into his home one night, Hutch's unknown long-simmering rage is ignited and propels him on a brutal path that will uncover dark secrets he fought to leave behind.",
            releaseDate = "2021-03-26",
            voteAverage = 8.4,
            id = 615457,
            title = "Nobody",
            genreIds = listOf(28, 53, 80, 35),
            posterPath = "/oBgWY00bEFeZ9N25wWVyuQddbAo.jpg",
        ),
    )

    fun generateDetailMoviesDto(): DetailMoviesDto =
        DetailMoviesDto(
            overview = "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
            originalLanguage = "English",
            originalTitle = "Mortal Kombat",
            runtime = 110,
            title = "Mortal Kombat",
            posterPath = "/nkayOAUBUu4mMvyNf9iHSUiPjF1.jpg",
            backdropPath = "/6ELCZlTA5lGUops70hKdB83WJxH.jpg",
            spokenLanguages = listOf(
                MoviesSpokenLanguagesDto(
                    "English", "en", "English"
                ),
            ),
            revenue = 76706000,
            productionCompanies = listOf(
                MoviesProductionCompaniesDto(
                    logoPath = "/wChlWsVgwSd4ZWxTIm8PTEcaESz.png",
                    name = "Atomic Monster",
                    id = 76907,
                    originCountry = "US"
                )
            ),
            releaseDate = "2021-04-07",
            genres = listOf(
                MoviesGenresDto(
                    "Action",
                    28,
                ),
                MoviesGenresDto(
                    "Fantasy",
                    14,
                ),
                MoviesGenresDto(
                    "Adventure",
                    12,
                ),
            ),
            voteAverage = 7.6,
            productionCountries = listOf(
                MoviesProductionCountriesDto("United States of America")
            ),
            tagline = "Get over here.",
            id = 460465,
            voteCount = 2630,
            budget = 20000000
        )

    fun generateMoviesModel(): List<MoviesModel> =
        generateListMoviesDto().toModels()

    fun generateDetailMoviesModel(): DetailMoviesModel =
        generateDetailMoviesDto().toModel()

    //    Shows
    fun generateShowsDtoResponse(): ShowsDtoResponse =
        ShowsDtoResponse(page = 1, generateListShowsDto())

    private fun generateListShowsDto(): List<ShowsDto> = listOf(
        ShowsDto(
            firstAirDate = "2021-03-24",
            overview = "Hell-bent on exacting revenge and proving he was framed for his sister's murder, Álex sets out to unearth much more than the crime's real culprit.",
            voteAverage = 7.8,
            name = "Who Killed Sara?",
            id = 120168,
            genreIds = listOf(18, 80, 9648),
            posterPath = "/o7uk5ChRt3quPIv8PcvPfzyXdMw.jpg",
        ),
        ShowsDto(
            firstAirDate = "2014-10-07",
            overview = "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
            voteAverage = 7.7,
            name = "The Flash",
            id = 60735,
            genreIds = listOf(18, 10765),
            posterPath = "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
        ),
        ShowsDto(
            firstAirDate = "2017-09-25",
            overview = "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives",
            voteAverage = 8.6,
            name = "The Good Doctor",
            id = 71712,
            genreIds = listOf(18),
            posterPath = "/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
        ),
        ShowsDto(
            firstAirDate = "2021-03-19",
            overview = "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
            voteAverage = 7.9,
            name = "The Falcon and the Winter Soldier",
            id = 88396,
            genreIds = listOf(10765, 10759, 18, 10768),
            posterPath = "/6kbAMLteGO8yyewYau6bJ683sw7.jpg",
        ),
        ShowsDto(
            firstAirDate = "2021-03-26",
            overview = "Mark Grayson is a normal teenager except for the fact that his father is the most powerful superhero on the planet. Shortly after his seventeenth birthday, Mark begins to develop powers of his own and enters into his father’s tutelage.",
            voteAverage = 8.9,
            name = "Invincible",
            id = 95557,
            genreIds = listOf(16, 10759, 18, 10765),
            posterPath = "/yDWJYRAwMNKbIYT8ZB33qy84uzO.jpg",
        ),
        ShowsDto(
            firstAirDate = "2021-03-24",
            overview = "Hell-bent on exacting revenge and proving he was framed for his sister's murder, Álex sets out to unearth much more than the crime's real culprit.",
            voteAverage = 7.8,
            name = "Who Killed Sara?",
            id = 120168,
            genreIds = listOf(18, 80, 9648),
            posterPath = "/o7uk5ChRt3quPIv8PcvPfzyXdMw.jpg",
        ),
        ShowsDto(
            firstAirDate = "2014-10-07",
            overview = "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
            voteAverage = 7.7,
            name = "The Flash",
            id = 60735,
            genreIds = listOf(18, 10765),
            posterPath = "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
        ),
        ShowsDto(
            firstAirDate = "2017-09-25",
            overview = "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives",
            voteAverage = 8.6,
            name = "The Good Doctor",
            id = 71712,
            genreIds = listOf(18),
            posterPath = "/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
        ),
        ShowsDto(
            firstAirDate = "2021-03-19",
            overview = "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
            voteAverage = 7.9,
            name = "The Falcon and the Winter Soldier",
            id = 88396,
            genreIds = listOf(10765, 10759, 18, 10768),
            posterPath = "/6kbAMLteGO8yyewYau6bJ683sw7.jpg",
        ),
        ShowsDto(
            firstAirDate = "2021-03-26",
            overview = "Mark Grayson is a normal teenager except for the fact that his father is the most powerful superhero on the planet. Shortly after his seventeenth birthday, Mark begins to develop powers of his own and enters into his father’s tutelage.",
            voteAverage = 8.9,
            name = "Invincible",
            id = 95557,
            genreIds = listOf(16, 10759, 18, 10765),
            posterPath = "/yDWJYRAwMNKbIYT8ZB33qy84uzO.jpg",
        ),
    )

    fun generateDetailShowsDto(): DetailShowsDto =
        DetailShowsDto(
            firstAirDate = "2021-03-26",
            overview = "Mark Grayson is a normal teenager except for the fact that his father is the most powerful superhero on the planet. Shortly after his seventeenth birthday, Mark begins to develop powers of his own and enters into his father’s tutelage.",
            seasons = listOf(
                ShowsSeasonsDto(
                    "2021-03-26",
                    "",
                    8,
                    "Season 1",
                    1,
                    136020,
                    "/yDWJYRAwMNKbIYT8ZB33qy84uzO.jpg"
                )
            ),
            numberOfEpisodes = 8,
            createdBy = listOf(),
            lastEpisodeToAir = ShowsLastEpisodeToAirDto(
                "2021-04-30",
                "Mark must prove he's become the hero he's always wanted to be by stopping an unstoppable force.",
                8,
                "WHERE I REALLY COME FROM",
                1,
                2832752,
                "/ijDV8Z23iR5B2ftx0WggiXbfqGi.jpg",
            ),
            posterPath = "/yDWJYRAwMNKbIYT8ZB33qy84uzO.jpg",
            backdropPath = "/6UH52Fmau8RPsMAbQbjwN3wJSCj.jpg",
            spokenLanguages = listOf(
                ShowsSpokenLanguagesDto(
                    "English",
                    "en",
                    "English"
                )
            ),
            productionCompanies = listOf(
                ShowsProductionCompaniesDto(
                    "/tkFE81jJIqiFYPP8Tho57MXRQEx.png",
                    "Amazon Studios",
                    20580,
                    "US"
                )
            ),
            genres = listOf(
                ShowsGenresDto("Animation", 16),
                ShowsGenresDto("Action & Adventure", 10759),
                ShowsGenresDto("Drama", 18),
                ShowsGenresDto("Sci-Fi & Fantasy", 10765),
            ),
            originalName = "Invincible",
            voteAverage = 8.9,
            name = "Invincible",
            tagline = "",
            episodeRunTime = listOf(45),
            id = 95557,
            numberOfSeasons = 1,
            nextEpisodeToAir = ShowsNextEpisodeToAirDto(),
            voteCount = 1789,
        )

    fun generateShowsModel(): List<ShowsModel> =
        generateListShowsDto().toModels()

    fun generateDetailShowsModel(): DetailShowsModel =
        generateDetailShowsDto().toModel()

    //  Genres
    fun generateGenresDtoResponse(): GenresDtoResponse =
        GenresDtoResponse(
            genres = generateListGenresDto()
        )

    fun generateGenresDtoResponseEmpty(): GenresDtoResponse =
        GenresDtoResponse(
            genres = emptyList()
        )

    private fun generateListGenresDto(): List<GenresDto> =
        listOf(
            GenresDto(name = "Action", id = 28),
            GenresDto(name = "Comedy", id = 35),
            GenresDto(name = "Animation", id = 16),
            GenresDto(name = "Drama", id = 18),
            GenresDto(name = "Horror", id = 27),
            GenresDto(name = "History", id = 36),
            GenresDto(name = "Science Fiction", id = 878),
            GenresDto(name = "Romance", id = 10749),
        )

    fun generateSearchMoviesDtoResponse(): SearchDtoResponse = SearchDtoResponse(
        1, generateListSearchMoviesDto(), generateListSearchMoviesDto().size
    )

    fun generateSearchShowsDtoResponse(): SearchDtoResponse = SearchDtoResponse(
        1, generateListSearchShowsDto(), generateListSearchShowsDto().size
    )

    private fun generateListSearchMoviesDto(): List<SearchDto> =
        generateListMoviesDto().map { response ->
            SearchDto(
                mediaType = "movie",
                name = response.title,
                id = response.id,
                overview = response.overview,
                title = response.title,
                genreIds = response.genreIds,
                posterPath = response.posterPath,
                releaseDate = response.releaseDate,
                voteAverage = response.voteAverage,
                firstAirDate = response.releaseDate
            )
        }

    private fun generateListSearchShowsDto(): List<SearchDto> =
        generateListShowsDto().map { response ->
            SearchDto(
                mediaType = "tv",
                name = response.name,
                id = response.id,
                overview = response.overview,
                title = response.name,
                genreIds = response.genreIds,
                posterPath = response.posterPath,
                releaseDate = response.firstAirDate,
                voteAverage = response.voteAverage,
                firstAirDate = response.firstAirDate
            )
        }

    fun generateSearchMoviesModel(): List<SearchModel> =
        generateListSearchMoviesDto().map { response ->
            SearchModel(
                mediaType = "movie",
                id = response.id,
                overview = response.overview.toString(),
                title = response.title.toString(),
                name = response.title.toString(),
                posterPath = response.posterPath.toString(),
                releaseDate = response.releaseDate.toString(),
                voteAverage = response.voteAverage ?: 0.0,
                firstAirDate = response.releaseDate.toString()
            )
        }

    fun generateSearchShowsModel(): List<SearchModel> =
        generateListSearchShowsDto().map { response ->
            SearchModel(
                mediaType = "tv",
                id = response.id,
                overview = response.overview.toString(),
                title = response.title.toString(),
                name = response.title.toString(),
                posterPath = response.posterPath.toString(),
                releaseDate = response.releaseDate.toString(),
                voteAverage = response.voteAverage ?: 0.0,
                firstAirDate = response.releaseDate.toString()
            )
        }

    fun generateSearchModel(): List<SearchModel> =
        generateSearchMoviesModel() + generateSearchShowsModel()

    fun generateFavoritesWithGenres(): List<FavoritesWithGenres> = listOf(
        FavoritesWithGenres(
            favorites = FavoritesEntity(
                1, "Lorem Ipsum",
                "Lorem Ipsum",
                "2020-19-20",
                3.4,
                "posterPath",
                "movies"
            ),
            genres = listOf(
                GenresEntity(1, 1, 101, "Action"),
                GenresEntity(2, 1, 102, "Adventure"),
            )
        ),
        FavoritesWithGenres(
            favorites = FavoritesEntity(
                1, "Lorem Ipsum",
                "Lorem Ipsum",
                "2020-19-20",
                3.4,
                "posterPath",
                "movies"
            ),
            genres = listOf(
                GenresEntity(1, 1, 101, "Action"),
                GenresEntity(2, 1, 102, "Adventure"),
            )
        ),
        FavoritesWithGenres(
            favorites = FavoritesEntity(
                1, "Lorem Ipsum",
                "Lorem Ipsum",
                "2020-19-20",
                3.4,
                "posterPath",
                "movies"
            ),
            genres = listOf(
                GenresEntity(1, 1, 101, "Action"),
                GenresEntity(2, 1, 102, "Adventure"),
            )
        ),
        FavoritesWithGenres(
            favorites = FavoritesEntity(
                1, "Lorem Ipsum",
                "Lorem Ipsum",
                "2020-19-20",
                3.4,
                "posterPath",
                "movies"
            ),
            genres = listOf(
                GenresEntity(1, 1, 101, "Action"),
                GenresEntity(2, 1, 102, "Adventure"),
            )
        ),
        FavoritesWithGenres(
            favorites = FavoritesEntity(
                1, "Lorem Ipsum",
                "Lorem Ipsum",
                "2020-19-20",
                3.4,
                "posterPath",
                "movies"
            ),
            genres = listOf(
                GenresEntity(1, 1, 101, "Action"),
                GenresEntity(2, 1, 102, "Adventure"),
            )
        ),
        FavoritesWithGenres(
            favorites = FavoritesEntity(
                1, "Lorem Ipsum",
                "Lorem Ipsum",
                "2020-19-20",
                3.4,
                "posterPath",
                "movies"
            ),
            genres = listOf(
                GenresEntity(1, 1, 101, "Action"),
                GenresEntity(2, 1, 102, "Adventure"),
            )
        ),
        FavoritesWithGenres(
            favorites = FavoritesEntity(
                1, "Lorem Ipsum",
                "Lorem Ipsum",
                "2020-19-20",
                3.4,
                "posterPath",
                "movies"
            ),
            genres = listOf(
                GenresEntity(1, 1, 101, "Action"),
                GenresEntity(2, 1, 102, "Adventure"),
            )
        ),
        FavoritesWithGenres(
            favorites = FavoritesEntity(
                1, "Lorem Ipsum",
                "Lorem Ipsum",
                "2020-19-20",
                3.4,
                "posterPath",
                "movies"
            ),
            genres = listOf(
                GenresEntity(1, 1, 101, "Action"),
                GenresEntity(2, 1, 102, "Adventure"),
            )
        ),
        FavoritesWithGenres(
            favorites = FavoritesEntity(
                1, "Lorem Ipsum",
                "Lorem Ipsum",
                "2020-19-20",
                3.4,
                "posterPath",
                "movies"
            ),
            genres = listOf(
                GenresEntity(1, 1, 101, "Action"),
                GenresEntity(2, 1, 102, "Adventure"),
            )
        ),
        FavoritesWithGenres(
            favorites = FavoritesEntity(
                1, "Lorem Ipsum",
                "Lorem Ipsum",
                "2020-19-20",
                3.4,
                "posterPath",
                "movies"
            ),
            genres = listOf(
                GenresEntity(1, 1, 101, "Action"),
                GenresEntity(2, 1, 102, "Adventure"),
            )
        ),
    )

    fun generateFavoritesModel(): List<FavoritesModel> =
        generateFavoritesWithGenres().map { favoritesWithGenres ->
            FavoritesModel(
                favoritesWithGenres.favorites.id,
                favoritesWithGenres.favorites.name,
                favoritesWithGenres.favorites.overview,
                favoritesWithGenres.favorites.releaseDate,
                favoritesWithGenres.genres.map { it.toModel() },
                favoritesWithGenres.favorites.voteAverage,
                favoritesWithGenres.favorites.posterPath,
                FavoriteItemType.valueOf(StringMapper.capitalize(favoritesWithGenres.favorites.type)),
            )
        }

}