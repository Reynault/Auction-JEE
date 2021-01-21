// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  backend: {
    protocol: 'http',
    host: 'localhost',
    port: '8080',
    endpoints: {
      oneUsers: '/users/:id',
      oneUserByUsername: '/users/username/:username',
      allUsers: '/users', // List all users
      login: '/auction/login', // Login
      register: '/auction/register', // Register
      articles: '/auction/articles', // List all auctions
      article: '/auction/articles/:id', // Get information on an article
      addArticle: '/auction/articles/submit', // Add an article (Connected User)
      allUserArticles: '/auction/articles/my', // List all user's articles (Connected User)
      articleUser: '/auction/articles/:id/my', // Get information on a specific article user own (Connected User)
      addAuction: '/auction/articles/:id/sell', // Create Auction (Connected User)
      deleteAuction: '/auction/articles/:id/remove', // Delete Auction (Connected User)
      deleteArticle: '/auction/articles/:id/delete', // Delete Article (Connected User)
      promotions: '/auction/participation/promotions', // Get all products who have promotion (connected User)
      participate: '/auction/participation/:id', // Add a participation to an Auction (Connected User)
      participations: '/auction/participation/my'
    }
  }
};


/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
