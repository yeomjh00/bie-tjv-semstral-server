# BIE-TJV-Semester-Project \<Social Network System with Media\>
# Tools
- JDK 17
- Spring Boot 3.2.0
- H2 DB 2.2.224 → Will be replaced by MySQL
- Spring Data JPA 2.6.0
- Gradle 8.5

# Data
![DataConceptualRelation](./images/data_conceptual_relation_new.png)
## User
### Field (Name: Description / Data Type / Nullable)
 - UserId: Identifier for user / Long / Not Null
 - RealName: Real name of user / String / Null
 - String status: status of user / String / Not Null
 - PostLikedByMe: Post I liked / Collection\<Post> / Null
 - MyPosts: Post I uploaded / Collection\<Post> / Null
 - MyMusicList: Music list I made by adding from Music Database / Collection\<Post> / Null

### Relation
with `Post`: 
- Many-to-Many Relation: "Like" system
- One-to-Many Relation: Creating by User

with `MusicList`:
 - One-to-Many Relation: Creating by User

## Post
### Field (Name: Description / Data Type / Nullable)
- PostId: Identifier for Post / Long / Not Null
- Title: title of the post / String / Not Null
- TextContents: Text Contents / String / Not Null
- Author: Author of the post / User / Not Null
- Likes: user who like this post / Collection\<User> / Null
- Optional Field (Post may contain some of these fields)
  - Picture: Media contained in this post. can contain multiple media / Collection\<Picture> / Null
  - Music: Media contained in this post. can contain multiple musics / List\<Music> / Null

### Relation
with `Uesr`: see User Section

with `Picture`, `Music`:
 - One-to-Many Relation: Single media file can be indicated by multiple Posts

## Media
### Field (Name: Description / Data Type / Nullable)
1. Picture
- width: - / Long / Not Null
- height: - / Long / Not Null
2. Music
- MusicId: identifier of music / Long / Not Null
- PlayTime: playtime of each music / Long / Not Null
- PostedTimes: how many posted / Long / Not Null (Default: 0)

### Relation
with `Post`: see Post Section

with `MusicList`:
 - Many-to-One Relation: single music list can contain multiple musics at a time.

## MusicList
### Field (Name: Description / Data Type / Nullable)
 - ListId: identifier of the lists / Long / Not Null
 - Owner: owner of this list / User / Not Null
 - Musics: Musics contained by this list / Collection\<PlayableMedia> / Null

### Relation

with `User`: See User Section

with `Media`: See Media Section

---

# Query
- Basic CRUD queries for each entity
- Return number of music list owned by specific user. (For restriction) 

# Complex Business Operation Logic
Assumption: Behavior of Presentation Layer(user) is omitted. (Since I assumed all behaviors in this layer are just pressing the button as an action, update the window as a respondance)
![WorkFlow](./images/Entire_Work_Flow.png)

## User Status Degradation
When user's membership is expired(or, decide to degrade him/her status), after he/she push the button,

1. user client get some information about music lists
2. If it violates the restriction, it will be rejected.
3. If not, user client send request to server

**Trial member can have 5 music lists, and 30 musics in each list at a time.**

## Creating Music List, Adding Music to list.
Similarly, based on the restriction described above, user client get some information about music lists, and send request to server.
