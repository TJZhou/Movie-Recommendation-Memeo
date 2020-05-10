
export class MovieResponse extends Response {
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
