
# BattleBot
BattleBot is my submission to Discord Hack Week! Spend your down time in between games battling against your friends or even Wumpus in a fun, spammy way!

> ![enter image description here](https://imgur.com/kCl10SM.gif)
## Setup
You can compile the source with Maven or download the release jar.

*Java 8+ is required.*

**Running the Bot**

 1. **Run the jar.** 

> You may use -token argument to set your token 
> ``java -jar battle-bot.jar -token <token>`` 
> *Alternatively, your token can be set in your config.json generated after the first run.*


2. **Invite the Bot.** 

> The invite link will be logged after running the bot.
> Additionally for Discord Hack Week only, you can play with the bot in my test server [here](https://discord.gg/D4qhjbw) or [invite the bot!](https://discordapp.com/oauth2/authorize?scope=bot&client_id=592785625925550120&permissions=1073750016)

## Commands

![enter image description here](https://support.discordapp.com/hc/en-us/article_attachments/203595007/DiscordKnightMini.png)

 - !help - Lists avaliable Commands
 - !battle - Main battle command
 
| !battle spar | !battle dual @Player | !battle boss @Player | !battle wumpus @Player|
|-|-|-|-|
|Spar with a training dummy!|D-D-D-DUAL with your best friend! | Battle with your friends with one of you as the raid boss| Battle the legendary Wumpus with your friends! |
#### !battle spar
Spar with a Battle Dummy to familerise yourself with moves or to pass those long game queue times!

> Requests a dual with a BATLLE DUMMY.
> ![enter image description here](https://i.imgur.com/fmzvRC1.gif)

#### !battle dual @Player
Same as above, but with a real life friend! How swell! Only one will come out ***ALIVE***

> Requests a dual with the mentioned player.

#### !battle boss @Player @Player1 @Bob . . .
It's a party! Add one or ten ~~thousand~~ of your friends to duke it out in a spectacular **BATTLE OF THE CENTURY** where one of you will transform into a suped up, mega raid boss that will destroy everyone and the universe!

> Requests a boss battle with all mentioned players. One will be randomly chosen to be the boss while the rest are teamed up against the boss. (Recommended: 3+)
> ![enter image description here](https://imgur.com/YCFHKmX.gif)

#### !battle wumpus @Player @Player @Player44
Instead, gather one or two ~~thousand~~ of your friends to battle the one and only, the great, Wumpus, the overlord of Discord, the world, and the universe!

> Requests a boss battle with all mentioned players and Wumpus! All players will be teamed against Wumpus. (Recommended: 3+)

#### !battle end
Boo. Forcefully ends an ongoing battle. 

> The user must have the ADMINISTRATOR permission on the guild!

### !config
Reloads the config and stuff.

## The Battle
Each battle player will have their own battle panel. Battling is dead simple. Spam. Each button below the battle panel are your moves and the only way to win is to spam your opponent to death!

![enter image description here](https://i.imgur.com/fmzvRC1.gif)

## Basic Moves List
### 👊 Basic Attack Move

> **Does Damage. Sometimes double!**
> 
> Your primary button to smash! Your Basic Attack Move does what is
> labelled on the can. Attack.
> ![enter image description here](https://imgur.com/vfaI6ce.gif)

### ✊ Charged Attack Move

> **Also does damage. It requires charging. Sometimes it fails. But it does even more damage.**
> 
> Charge this baby up and fire a real knock out punch!
> ![enter image description here](https://i.imgur.com/fWbVDQb.gif)

### ❤️ Heal Move

> **Sometimes the damage is too much. Heal for more health!**
> 
> Realistically, pretty pointless. But hey it's there.
> ![enter image description here](https://imgur.com/KNQ2DpR.gif)

## EPIC RAID BOSS Moves List
### 👊 Boss Attack Move

> **Same old basic attack move. But targets players randomly!**
> 
> Spam, spam, spam! Attack a battle hero randomly!
> ![](https://imgur.com/Nej6jBI.gif)

### ⚔️ Sweep Attack Move

> **Charge and attack every battle hero in one fell swoop!**
> 
> A powerful move for an equally powerful boss!
> ![enter image description here](https://imgur.com/SCA4upf.gif)

### ❤️ Boss Heal Move

> **Heal. For. 1. Damage.**
> 
> I don't why this is here. But you heal for some damage!
> ![enter image description here](https://imgur.com/PhVPTEd.gif)

## Configuration - config.json
Whaaat? Epic battle music, too? Join with your friends in voice comms to the sound of \<insert your favorite hard core jams here\>.

```
{
	"token":"",
	"prefix":"!",
	"dual-music":"https://www.youtube.com/watch?v=gT-1L_WxYus",
	"wumpus-music":"https://www.youtube.com/watch?v=toNSuhgBKN8&t=17s",
	"boss-music":"https://www.youtube.com/watch?v=omC9XvFnbpY",
	"idle-timeout": 30
}
```
> You can replace the battle music with a YouTube link to your favorite song to jam while you spam!

>Idle timeout is in **seconds**. The amount of time required to pass without any moves from any players in order to end the battle! :(

# Closing Notes
Thank you to Discord for hosting a hack week! I got inspired to make this from a similar mechanic from my personal bot and also **boredom** those long game queue times. It was a lot of fun making this project. Thank you to my friends who helped test out my bot and their feedback.

## Important LINKS!

Join my test server to try out the bot [here](https://discord.gg/D4qhjbw). 

> For Discord Hack Week only, invite the bot using the link
> [here!](https://discordapp.com/oauth2/authorize?scope=bot&client_id=592785625925550120&permissions=1073750016) Managing emojis and messages are required permission! The bot is not built to scale with multiple guilds well, so there might be downtime, there might be bugs. For the best possible result, host your own local instance!

Open source libraries in my projects:

 - [JDA](https://github.com/DV8FromTheWorld/JDA)
 - [lavaplayer](https://github.com/sedmelluq/lavaplayer)
 - [Project Lombok](https://github.com/rzwitserloot/lombok)
 - [SL4J](https://github.com/qos-ch/slf4j)

![Discord Hack Week](https://cdn-images-1.medium.com/max/2600/1*lh6NS8hx0pu5mlZeSqnu5w.jpeg)

