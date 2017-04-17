# Group Project - *Spur*

### Group Members - *Amod Samant*, *Deonna Hodges*, *Divya Yadav*

## Description

Challenge one another find motivation to achieve goals.

User can create challenges and invite friends to set and complete personal goals. Members provide evidence of the steps taken to achieve their goals and share them with others.

## Terms

**Challenge**: A goal that a user is working toward along with >= 1 other users

**Challenge Type**: App supports two different challenge formats:
   1. Multi User challenge - User can add more than one friend to the challenge. 
   2. One on One challenge - User can add only one friend to the challenge.

**Timeline**: App hosts two different timelines for different challenge types. Each timeline has a user experience suited to the challenge types.

**Posts**: The user can submit a "post" that serves as proof of working toward a challenge.

There are four types of posts:

  1. Image posts - the user can post directly from the camera, or select an existing image from the phone's gallery.
  2. Video posts - the user posts video evidence of proof.
  3. Location posts  - the user's location and map view serves as proof (e.g., if the challenge is to visit the gym regularly, the user can post a map showing they went to the gym)
  4. Text posts - textual proof.

## Achieved Stories [Week 1 & 2]

The following **required** functionality is completed:

### Onboarding

* [x] User can authenticate via Facebook
  * [x] User profile is seeded with info from Facebook (name, email, profile photo, cover photo)
  * [x] User can add Facebook friends who are already users of Spur to challenges

### Creating challenge

* [x] Users can create two types of challenges: 
  * [x] Multi User challenge
  * [x] 1:1 challenge
* For each challenge, the user can set:
  * [x] Name
  * [x] Description
  * [x] Start date and end date
  * [x] Challenge type
  * [x] Post frequency
  * [x] Friends who wish to participate in the challenges 
  * [x] A photo that visually represents the challenge 
* [x] For each user, this data is validated and updated to Parse

### Posts as evidence of steps taken in a challenge
  * [x] User can create different post types
      * [x] Image
      * [x] Video
      * [x] Location
      * [x] Text
  * [x] Challenge members can like and comment on posts
  * [x] Challenge members can see the other users participating in the challenge
  * [x] Users belonging to a challenge can add more friends to the challenge
  * [x] User can leave a challenge
  * [x] User can delete the challenege
  * [x] User can view a detail screen for each post

### Notification
  * [x] User recieves a notification when added to a challenge

## Stretch Goals
  * [ ] App includes local data store to improve loading time
  * [ ] User's posting frequency is enforced to ensure users don't post too little or too frequently
  * [ ] User can view a scorecard of how well they are progressing
## Video Walkthrough
<a href="https://github.com/accountabilibuddies/accountabilibuddies/tree/master/assets/Walkthrough/MileStone%20week%202"> WEEK 2 GIF's</a>

## Wireframes
<a href="https://github.com/accountabilibuddies/accountabilibuddies/blob/master/assets/wireframes/v1.pdf"> WIREFRAME LINK </a>
