export class MovieResponse {
  code: number;
  msg: string;
  timestamp: string;
  data: Array<Movie>;
}

export class Movie {
  movieId: number;
  imdbId: number;
  genres: string;
  title: string;
  rating: string;
  rater: number;
}
