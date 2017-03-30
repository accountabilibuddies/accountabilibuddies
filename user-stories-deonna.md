# User Stories for Accountabilibuddies - Deonna

## Terms

**Challenge**: a goal that a user is working toward along with >= 1 other users

**Challenge Category**: a broad category that is predefined by the app and serves as an umbrella category for goals (compare to the broad categories Pinterest uses to categorize pins - e.g., ‘photography’, ‘music’, ‘literature’, etc.). Challenge categories are used to determine a user’s interests.

**Milestone**: a sub-challenge within a challenge that can be completed in < 1 week (is that a good bar).
Milestone suggestions: a list of suggestions provided by the app for good challenges (we can leverage already-existing challenges to see this)

**Timeline**: the view that allows the user to see progress within a challenge and compare their own progress to another user’s progress
Accountabilibuddy: another user that is participating in the challenge along with you

**Showoff Timeline**: timeline that allows users to show off the results of their milestones (e.g., photography challenge, album writing challenge)

**Motivation Timeline**: timeline that allows users to receive compliments, thumbs up, words of encouragement, etc. from their accountabilibuddies

**Competition Timeline**: timeline that allows users to directly compete head-to-head. One person “wins” each milestone (e.g., this may be hard to execute, because there is no ‘judge’ to determine the winner. For MVP, we can decide who “wins” based on who posts first.

## User Stories

The following **required** functionality is completed:

### Onboarding

* [ ] User can log in using OAuth
  * [ ] After logging in, user can see a list of challenge categories
  * [ ] User is onboarded by selecting at least one of the predefined challenge categories

### Inviting Other Users

* [ ] Users can invite others to join challenges
  * [ ] User can invite their contacts to join challenges (on the platform they used to authenticate with)
    * [ ] If the contact is not already a user, the app will send them an invitation to both install the app and join the challenge
    * [ ] If the contact is already a user, the app will send them an invitation to join the challenge
  * [ ] User can invite strangers who share their interests (have the same challenge categories) to join challenges
* [ ] User can accept an invitation to be another user’s challenge buddy

### Kicking Off Challenges

* [ ] User can kick off a challenge under a predefined challenge category
* [ ] User can kick off a challenge using a custom challenge category

* [ ] User can add predefined/suggested milestones to a challenge
* [ ] User can add custom milestones to a challenge

* [ ] User can kick off a motivation, showoff, or competitive challenge

### Viewing Challenges

* [ ] User can view a timeline that tracks progress towards a goal
  * [ ] User can view their own progress toward a goal
  * [ ] User can view their challenge buddy’s progress towards a goal

* [ ] User can view timelines that fit their needs
  * [ ] User can view a showoff timeline
  * [ ] User can view a motivation timeline
  * [ ] User can view a competition timeline

* [ ] User can choose to make a challenge public or private (public by default)
* [ ] User can add media evidence (photos, screenshots, videos) to each milestone
* [ ] User can share progress for each milestone on a challenge to social media

* [ ] User can automatically tag their challenge buddy on a social media post

* [ ] User can automatically tag their challenge buddy on a social media post

The following **optional** features are implemented:

The following **bonus** features are implemented:
