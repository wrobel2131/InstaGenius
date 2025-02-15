import { Injectable } from '@angular/core';
import {
  InMemoryDbService,
  ParsedRequestUrl,
  RequestInfo,
  RequestInfoUtilities,
  STATUS,
} from 'angular-in-memory-web-api';
import { Observable } from 'rxjs';
import { InMemoryDb, InstagramPostDb, UserDb } from '../models/mocked-data';
import { User, UserLoginCredentials } from '../models/user.model';
import { InstagramPost } from '../models/instagram-post.model';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb(): InMemoryDb {
    return {
      users: [
        {
          id: 0,
          email: 'user1@example.com',
          login: 'user1',
          firstName: 'John',
          lastName: 'Doe',
          password: 'user1',
          coins: 30,
        },
        {
          id: 1,
          email: 'user2@example.com',
          login: 'user2',
          firstName: 'Jane',
          lastName: 'Doe',
          password: 'user2',
          coins: 40,
        },
        {
          id: 2,
          email: 'user3@example.com',
          login: 'user3',
          firstName: 'Alice',
          lastName: 'Smith',
          password: 'user3',
          coins: 10,
        },
        {
          id: 3,
          email: 'user4@example.com',
          login: 'user4',
          firstName: 'Bob',
          lastName: 'Brown',
          password: 'user4',
          coins: 50,
        },
        {
          id: 4,
          email: 'user5@example.com',
          login: 'user5',
          firstName: 'Charlie',
          lastName: 'Davis',
          password: 'user5',
          coins: 0,
        },
      ] as UserDb[],
      posts: [
        {
          id: 1,
          userId: 0,
          title: 'Mystic Mountains',
          imageUrl: '/assets/images/mocked-images/image1.jpg',
          description:
            'Discover the serene beauty of the Mystic Mountains. These majestic peaks rise gracefully against the sky, cloaked in a blanket of lush greenery. Each step taken on these ancient trails reveals the timeless wonders of nature and the awe-inspiring power of creation. The air is filled with the scent of pine and the gentle whispers of the forest, inviting adventurers and dreamers alike to explore its hidden depths.',
        },
        {
          id: 2,
          userId: 0,
          title: 'Urban Jungle',
          imageUrl: '/assets/images/mocked-images/image2.jpg',
          description:
            'The Urban Jungle, a realm where the hustle of life meets the resilience of the human spirit. Towering skyscrapers, each a monument to human ingenuity, rise boldly into the sky, creating a skyline that captures the very essence of progress. The streets buzz with the energy of millions, each individual contributing to the vibrant tapestry of city life. Amidst the chaos lies an underlying rhythm, a heartbeat that pulses with the dreams and aspirations of those who call the city home.',
        },
        {
          id: 3,
          userId: 0,
          title: 'Oceans Whisper',
          imageUrl: '/assets/images/mocked-images/image3.jpg',
          description:
            'Oceans Whisper is a tribute to the vast and mysterious sea. The endless expanse of blue, stretching far beyond the horizon, speaks of mysteries untold and depths unexplored. Each wave that laps against the shore carries stories from distant lands, whispered secrets of the deep, and the timeless dance of water and wind. The ocean, in its boundless majesty, reminds us of the infinite possibilities that lie ahead and the enduring beauty of the natural world.',
        },
        {
          id: 4,
          userId: 0,
          title: 'Enchanted Forest',
          imageUrl: '/assets/images/mocked-images/image4.jpg',
          description:
            'Step into the Enchanted Forest, where magic lingers in every leaf and branch. This ancient woodland is a haven of tranquility and wonder, where nature’s artistry is displayed in a symphony of colors and textures. The forest whispers tales of yore, of mystical creatures and age-old legends, inviting wanderers to lose themselves in its emerald embrace. Here, amidst the chorus of wildlife and the rustle of leaves, one can truly connect with the earth and discover the enchanting mysteries it holds.',
        },
        {
          id: 5,
          userId: 0,
          title: 'Desert Mirage',
          imageUrl: '/assets/images/mocked-images/image5.jpg',
          description:
            'The Desert Mirage is a testament to the stark beauty of the wilderness. Beneath the relentless sun, the desert stretches out, an endless sea of sand and stone. The landscape is ever-changing, shaped by the winds of time, revealing hidden oases and ancient secrets. In this land of extremes, life perseveres, adapting and thriving in ways that captivate the imagination. The desert, with its golden dunes and starlit nights, offers a journey of discovery and reflection, a place where the soul can wander freely under the vast, open sky.',
        },
        {
          id: 6,
          userId: 0,
          title: 'City Lights',
          imageUrl: '/assets/images/mocked-images/image6.jpg',
          description:
            'City Lights is an ode to the nights that never sleep. As dusk falls, the city comes alive with a kaleidoscope of lights, each shining brightly against the canvas of the night. The streets, alive with the buzz of activity, showcase the vibrant culture and diversity of urban life. High above, the twinkling lights of skyscrapers mingle with the stars, creating a dreamscape of illumination. This city, a beacon of hope and dreams, stands as a testament to the enduring spirit of humanity and the unceasing quest for progress and connection.',
        },
        {
          id: 7,
          userId: 1,
          title: 'Autumn Symphony',
          imageUrl: '/assets/images/mocked-images/image7.jpg',
          description:
            'Autumn Symphony celebrates the melodious transformation of nature as it embraces the fall season. The landscape is painted in warm hues of orange, red, and yellow, each leaf telling a story of change and renewal. The crisp air is filled with the scent of harvest, and the rustling of leaves underfoot composes a symphony that resonates with the soul. This time of year is a gentle reminder of the beauty of letting go and the mesmerizing cycle of life.',
        },
        {
          id: 8,
          userId: 1,
          title: 'Starry Wilderness',
          imageUrl: '/assets/images/mocked-images/image8.jpg',
          description:
            'Starry Wilderness is an homage to the captivating beauty of the night sky. Far from the citys glare, the heavens unveil their splendor, a dazzling display of stars and constellations. The darkness of the night becomes a canvas for the cosmos, each star a distant world, a beacon of light in the vast wilderness of space. This celestial spectacle inspires awe and wonder, reminding us of our place in the universe and the infinite mysteries that lie beyond our world.',
        },
        {
          id: 9,
          userId: 1,
          title: 'Reflections of Serenity',
          imageUrl: '/assets/images/mocked-images/image2.jpg',
          description:
            'Reflections of Serenity captures the tranquil beauty of a still lake, its surface a perfect mirror to the world above. Surrounded by lush greenery, the water reflects the skys ever-changing moods, from the fiery hues of dawn to the soft pastels of dusk. The gentle lapping of the water whispers tales of peace and stillness, inviting the soul to pause and bask in the moment. This serene setting is a sanctuary for contemplation, a place where one can connect with nature and find inner harmony.',
        },
      ] as InstagramPostDb[],
    };
  }

  // Override the post method to simulate login
  post(reqInfo: RequestInfo) {
    console.log('post req');
    if (reqInfo.collectionName === 'login') {
      return this.login(reqInfo);
    }
    return undefined; // Let default POST operations be handled as usual
  }

  private login(reqInfo: RequestInfo) {
    // Extract the users collection from the in-memory database
    const db = reqInfo.utils.getDb() as InMemoryDb;
    const users = db.users;

    // Extract the login credentials from the request body
    const credentials: UserLoginCredentials = reqInfo.utils.getJsonBody(
      reqInfo.req
    );

    // Find the user
    const user = users.find(
      (u: UserDb) =>
        u.login === credentials.login && u.password === credentials.password
    );

    if (user) {
      return reqInfo.utils.createResponse$(() => {
        return {
          body: {
            accessToken: 'access token',
            refreshToken: 'refresh_token',
            userId: user.id,
          },
          status: STATUS.OK,
        };
      });
    } else {
      return reqInfo.utils.createResponse$(() => {
        return {
          body: { error: 'User not found or wrong password' },
          status: STATUS.UNAUTHORIZED,
        };
      });
    }
  }
}
