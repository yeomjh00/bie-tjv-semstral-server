# BIE-TJV-Semestral-Project
# Agriculture Management System
## Tools
- JDK 17
- MySQL

## Optional Goal
Based on posts which each users liked, introducing recommendation system.

## Build Tools
- Gradle (version not fixed)

## Data
![DataConceptualRelation](./images/data_conceptual_relation.png)
### User
#### Field (Name: Description / Data Type / Nullable)
 - UserId: Identifier for farmers / Long / Not Null
 - RealName: Real name of farmer / String / Null
 - PostLikedByMe: Post I liked / Collection\<Post> / Null
 - MyPosts: Post I uploaded / Collection\<Post> / Null
 - MyMusicList: Music list I made by adding from Music Database / Collection\<Post> / Null

#### Relation
with Post: 
- Many to Many Relation: "Like" system
- One to Many Relation: Creating by User

with Music List:
 - One to May Relation: Creating by User

### Post
#### Field (Name: Description / Data Type / Nullable)
- PostId: Identifier for Post / Long / Not Null
- TextContents: Text Contents / String / Not Null
- Author: Author of the post / User / Not Null
- Likes: user who like this post / Collection\<User> / Null
- NonplayableMedia: Media contained in this post. can contain multiple media (i.e. Photos) / Collection\<NonplayableMedia> / Null
- PlayableMedia: Media contained in this post. cna only contain single media (i.e. Music) / PlayableMedia / Null
- Optional Field
  - Replies: Replies of this post / Collection\<Post> / Null
  - ReplyTo: target of reply of this oost / Optional\<Post> / Null

#### Relation
with Uesr: see User Section

with Media:
 - One to May Relation: Contained by Posts

### Media
#### Field (Name: Description / Data Type / Nullable)
1. NonplayableMedia
 - width: - / Long / Not Null
 - height: - / Long / Not Null

2. PlayableMedia
- MusicId: identifier of music / Long / Not Null
- PlayTime: playtime of each music / Long / Not Null
- PostedTimes: how many posted / Long / Not Null (Default: 0)

#### Relation
with Post: see Post Section

with Music List:
 - Many to One Relation: single music list can contain multiple musics at a time.

### MusicList
#### Field (Name: Description / Data Type / Nullable)
 - ListId: identifier of the lists / Long / Not Null
 - Owner: owner of this list / User / Not Null
 - Musics: Musics contained by this list / Collection\<PlayableMedia> / Null

#### Relation

with User: See User Section

with Media: See Media Section

---

## Query
### Basic Query
- Find the musics which I like - contained in my music list or, post I liked.
- Find the music with liked more than user-given-threshold 

### Using Subquery
Based on threshold given by user, find the liked musics which have posted time more than the threshold.

### Using Join
find the posts which contain the music with more posted times than user-given-threshold.

## Business Operation Logic
Assume
1. we have 3 layers (Presentation / Application / Persistence)
2. Behavior of Presentation Layer is omitted. (Since I assumed all behaviors in this layer are just pressing the button as an action, update the window as a respondance)
w

1. Creation of Posts
   1. write `Insert` query into post database

2. Like System
   1. Like
      1. Get liked users through db, judge whether user is unique
      2. Depend on the result of judge, write query for updating
      3. Let persistance layer accept this query

   2. See LikedByMe Posts
      1. Write `Select` query from Post Repository and 



d

Presentation Layer / Application Layer (=Business Layer) / Persistent Layer 로 나눠져 있는데, 각 행동마다 어떻게 Layer가 행동하는지에 대해 적으면 될듯 하다.