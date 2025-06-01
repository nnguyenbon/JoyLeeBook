INSERT INTO [dbo].[Users] ([role_name], [username], [email], [user_password])
VALUES
('admin', 'superuser', 'super@example.com', CONVERT(VARCHAR(32), HASHBYTES('MD5', 'superuser'), 2)),
('reader', 'mark_olson', 'mark.o@example.com', CONVERT(VARCHAR(32), HASHBYTES('MD5', 'markeditpass'), 2)),
('reader', 'lisa_ray', 'lisa.r@example.com', CONVERT(VARCHAR(32), HASHBYTES('MD5', 'lisaview@00'), 2)),
('reader', 'dave_chen', 'dave.c@example.com', CONVERT(VARCHAR(32), HASHBYTES('MD5', 'editordave#123'), 2)),
('reader', 'emily_jones', 'emily.j@example.com', CONVERT(VARCHAR(32), HASHBYTES('MD5', 'vieweremily$'), 2));

INSERT INTO [dbo].[Genres] ([genre_name])
VALUES
('Action'),
('Adventure'),
('Adult'),
('Comedy'),
('Drama'),
('Eastern'),
('Fantasy'),
('Folklore'),
('Game'),
('Gender Bender'),
('Harem'),
('Historical'),
('Horror'),
('Isekai'),
('Josei'),
('Legend'),
('Magic'),
('Magical Realism'),
('Manhua'),
('Martial'),
('Martial Arts'),
('Mature'),
('Mecha'),
('Mystery'),
('Mythology'),
('Psychological'),
('Reincarnation'),
('Romance'),
('School Life'),
('Sci-fi'),
('Seinen'),
('Shoujo'),
('Shoujo Ai'),
('Shounen'),
('Shounen Ai'),
('Slice of Life'),
('Sports'),
('Supernatural'),
('System'),
('Tragedy'),
('Video Games'),
('Wuxia'),
('Xianxia'),
('Xuanhuan');

INSERT INTO [dbo].[Series] (
    [author_name],
    [series_title],
    [status],
    [description],
    [cover_image_url]
)
VALUES (
    N'Unknown',
    N'Mai An Tiem (The Legend of Mai An Tiem)',
    1, 
    N'(Vietnamese folklore, passed down through oral tradition) Mai An Tiem, 
	a talented young man from a distant southern land, is sold into slavery 
	and purchased by Hùng Vương. His intelligence and skills earn him favor, 
	leading to his promotion as a close attendant and marriage to the king’s
	adopted daughter. Despite his respected status, jealousy among court 
	officials leads to his downfall when his humble remark about his wealth 
	being the result of his past life is misconstrued as arrogance. Enraged, 
	Hùng Vương exiles Mai, his wife, and their young son to a deserted island 
	with limited provisions. Undeterred, Mai’s family adapts, finding water 
	and shelter. A flock of birds drops seeds that grow into watermelons, 
	which Mai names “Western melons.” These melons sustain the family and 
	become a valuable trade item with passing boats, bringing rice and goods. 
	Mai’s innovation spreads the watermelon across the land, earning him and 
	his wife the title “Father and Mother of the Western Melon.” When the king 
	learns of Mai’s success, he regrets his decision, recalls Mai, and restores
	his position. The island is named An Tiem Beach, and a temple is built to
	honor Mai and his wife as the “Ancestors of the Watermelon.” The legend 
	celebrates resilience, ingenuity, and divine providence, explaining the 
	origin of the watermelon in Vietnamese culture.',
    N'https://example.com/mai_an_tiem_cover.jpg' 
),
(
    N'Unknown',
    N'The Clever Boy',
    1,
    N'(Vietnamese folklore, passed down through oral tradition) During a time when the Hùng King sought talented individuals to aid the nation, an official is sent to find a wise person but returns empty-handed after posing unsolvable riddles. In a village, he encounters a clever boy, aged seven or eight, who counters his question about a buffalo’s plowing with a witty question about a horse’s steps. Impressed, the official reports to the king, who tests the boy by ordering his village to make three male buffalo produce nine offspring. The boy advises the village to feast on two buffalo and sell the third to fund a trip to the capital. There, he cleverly exposes the absurdity of the king’s order by asking the king to make his father produce a sibling, earning the king’s admiration. In a second test, the boy ingeniously suggests forging a needle into a knife to prepare three dishes from a sparrow. Finally, when a neighboring country poses a riddle about threading a string through a spiral seashell, the boy’s solution—using an ant tied with thread—secures victory. The king names him trạng nguyên (top scholar) and builds him a residence near the palace. The tale celebrates intelligence, quick wit, and the ability of a young commoner to outsmart challenges, reflecting Vietnam’s cultural value of wisdom over status.',
    N'https://example.com/the_clever_boy_cover.jpg'
),
(
    N'ZeusTheOlympian',
    N'Monarch of Time',
    0,
    N'The ultimate power that controls everything... Time. Rock, reincarnated in Shun Long''s half-dead body when a mysterious rock merged with him, and a sudden influx of information flooded his head. He is now cultivating in a mysterious technique that can affect time, as he adventures through the cultivation world fighting geniuses, while he reaches the pinnacle in both martial arts and alchemy.',
    N'https://example.com/monarch_of_time_cover.jpg'
),
(
    N'Guiltythree',
    N'Shadow Slave',
    0,
    N'Growing up in poverty, Sunny never expected anything good from life. However, even he did not anticipate being chosen by the Nightmare Spell and becoming one of the Awakened - an elite group of people gifted with supernatural powers. Transported into a ruined magical world, he found himself facing against terrible monsters - and other Awakened - in a deadly battle of survival. What''s worse, the shadow powers he received happened to possess a small, but potentially fatal side effect...',
    N'https://example.com/shadow_slave_cover.jpg'
),
(
    N'Unknown',
    N'The Tale of Coconut Skull',
    1,
    N'(Vietnamese folklore, passed down through oral tradition) A childless couple, lifelong servants to a wealthy landlord, face hardship when the husband dies, and the wife, after drinking from a skull in a forest, miraculously gives birth to Sọ Dừa, a limbless, skull-like being. Despite initial rejection, the mother nurtures him, and he grows wise and beloved. Sọ Dừa secretly transforms into a handsome boy to do chores and later proves his skill by herding the landlord’s goats. Smitten with the landlord’s kind youngest daughter, he seeks her hand in marriage. The landlord, skeptical, demands an extravagant dowry, which Sọ Dừa miraculously provides. The youngest daughter agrees to marry him, while her envious sisters refuse. At the wedding, Sọ Dừa reveals his true, handsome form, delighting all but the sisters. He becomes a top scholar and royal envoy. The jealous sisters attempt to drown their sister at sea, but she survives inside a giant fish, using tools Sọ Dừa gave her, and lives on a deserted island until he rescues her. At a feast celebrating his return, Sọ Dừa reveals her survival, shaming the sisters, who flee. The tale, set in a mythical past, celebrates kindness, ingenuity, and divine providence, explaining themes of transformation and justice in Vietnamese folklore.',
    N'https://example.com/the_tale_of_coconut_skull_cover.jpg'
),
(
    N'KaeNovels',
    N'Dungeon Diver: Stealing A Monster’s Power',
    0,
    N'A story following a young hunter named Jay. He has grown up in a world where dungeons, monsters, and humans with leveling systems are a cultural norm. At the age of 20, he awakens a skill that allows him to steal the abilities of monsters. While others are stuck with a specific skill set, he continues to grow stronger after every battle. Follow Jay''s journey as he learns more about his unique situation in a world that''s grown blind to the real dangers of modern-day dungeon diving.',
    N'https://example.com/dungeon_diver_cover.jpg'
),
(
    N'Cuttlefish That Loves Diving',
    N'Embers Ad Infinitum',
    0,
    N'In this latest work by Lord of the Mysteries author, Cuttlefish That Loves Diving, be prepared for a well-thought out and detailed apocalyptic, cyberpunk world with a setting superseding Lord of the Mysteries! Our protagonist, Shang Jianyao, is crazy—literally crazy, at least that’s what the doctors said. Living in a huge, underground building of Pangu Biology, one of the few remaining factions in this apocalyptic wasteland known as the Ashlands, he acts in unfathomable ways that’s head-scratching, comical, and shrewd. So is he really crazy? Probably. He has a grand dream: to save all of humanity. Intricately tied to this dream is something everyone in the Ashlands believes in: Deep in a particular ruin buried away by danger and famine, a path leading to a new world awaits. To step into the new world, one only needs to find a special key and open that certain door. There, the land is bountiful, as if milk and honey flows freely. The sunlight is dazzling, as if all coldness and darkness are washed away. The people will no longer have to face desolation, monsters, infections, mutations, and all kinds of dangers. There, children are joyous, adults are happy, everything is fine as they are supposed to be. Every Antiquarian, Ruin Hunter, and Historian roaming the Ashlands knows: That’s the New World.',
    N'https://example.com/embers_ad_infinitum_cover.jpg'
),
(
    N'Bamboo City''s Little Overlord',
    N'Evolving infinitely from ground zero',
    0,
    N'Infant Stage, a cumulative total of 10,000 leg kicks. Attribute gained: Use It or Lose It. As long as you continuously exercise the same body part, that part can evolve infinitely, but it can''t break the limits of carbon-based lifeforms….. Student Phase, a cumulative total of 100 million characters read. Attribute gained: Heavenly Intellect. Photographic memory, learning ability reached the pinnacle of humanity. …. Adult Stage, a cumulative matching of carbon-based lifeforms'' limits 100 times. Attribute gained: Multi-form. The body is no longer limited to the frailty of carbon-based lifeforms'' flesh and blood, capable of evolving beyond life forms…. Having transmigrated, Lin Zichen was born with an achievement-based cheat. In this life, he only wanted to evolve quietly, continuously breaking through the limits of life forms, and savoring the pleasure that comes with the leveling-up process.',
    N'https://example.com/evolving_infinitely_cover.jpg'
),
(
    N'秋晴雨生',
    N'Goddess Medical Doctor',
    1,
    N'She was dressed in white, with a mysterious orchid imprint on her forehead, her skin like jade is always covered with a veil. Since childhood, she had left her parents due to her special status and lived her life with her master in the clouds. She was not yet ten when her medicinal skills were renowned in the world. In order to save the people, she descended the mountains, however, she encountered a storm on the way to the epidemic area. The man who saved her would tease her for enjoyment, little did they know…',
    N'https://example.com/goddess_medical_doctor_cover.jpg'
),
(
    N'J.K. Rowling',
    N'Harry Potter and the Sorcerer’s Stone',
    1,
    N'Adaptation of the first of J.K. Rowling''s popular children''s novels about Harry Potter, a boy who learns on his eleventh birthday that he is the orphaned son of two powerful wizards and possesses unique magical powers of his own. He is summoned from his life as an unwanted child to become a student at Hogwarts, an English boarding school for wizards. There, he meets several friends who become his closest allies and help him discover the truth about his parents'' mysterious deaths.',
    N'https://example.com/harry_potter_sorcerers_stone_cover.jpg'
),
(
    N'Adui',
    N'Infinite Mana In The Apocalypse',
    0,
    N'Blessed with unlimited mana, Noah travels the worlds and sees rampant corruption and injustice. Have you seen countless icebergs fall asunder? Have you watched a dragon despair? Follow one man as he overturns the order of the worlds... The concepts appearing in this story are those of pure fantasy and fiction, they are not reflective of the real world. Everything is based on pure imagination.',
    N'https://example.com/infinite_mana_apocalypse_cover.jpg'
),
(
    N'Unknown',
    N'The Legend of Sơn Tinh and Thủy Tinh',
    1,
    N'(Vietnamese folklore, passed down through oral tradition) During the reign of the 18th Hùng King, Princess Mỵ Nương, renowned for her beauty, reaches marriageable age. The king seeks a worthy suitor, prompting the king of Tây Âu to offer lavish gifts, but his proposal is rejected due to his violent nature and odd appearance, straining relations between Văn Lang and Tây Âu. Two exceptional suitors, Sơn Tinh (God of Tản Viên Mountain) and Thủy Tinh (God of Water), compete for Mỵ Nương’s hand. Both display extraordinary powers: Sơn Tinh creates mountains and forests, while Thủy Tinh summons floods and sea creatures. Unable to choose between their equal talents, the king sets a challenge: the first to deliver a dowry of land-based items (sticky rice, bánh chưng, and mythical animals) will marry Mỵ Nương. Sơn Tinh, favored by the land-based requirements, arrives first and weds the princess. Enraged, Thủy Tinh pursues them, unleashing floods and storms. The people of Văn Lang build dikes to combat the floods, while Sơn Tinh raises mountains to block the waters. After a prolonged battle, Thủy Tinh retreats, exhausted. The rivalry persists, with Thủy Tinh annually causing floods in the seventh lunar month, only to be defeated by Sơn Tinh each time. This legend explains Vietnam’s flooding phenomena and the cultural practice of flood control through dikes, symbolizing the eternal struggle between land and water.',
    N'https://example.com/son_tinh_thuy_tinh_cover.jpg'
),
(
    N'Legion20',
    N'Supreme Magus',
    0,
    N'Derek McCoy was a man who spent his entire life facing adversity and injustice. After being forced to settle with surviving rather than living, he had finally found his place in the world, until everything was taken from him one last time. After losing his life to avenge his murdered brother, he reincarnates until he finds a world worth living in, a world filled with magic and monsters. Follow him along his journey, from grieving brother to alien soldier. From infant to Supreme Magus.',
    N'https://example.com/supreme_magus_cover.jpg'
),
(
    N'Fuse',
    N'Tensei Shitara Slime Datta Ken (WN)',
    1,
    N'A man is stabbed by a robber on the run after pushing his coworker and his coworker’s new fiance out of the way. As he lays dying, bleeding on the ground, he hears a voice. This voice is strange and interprets his dying regret of being a virgin by giving him the [Great Sage] unique skill! Is he being made fun of ?!',
    N'https://example.com/tensura_wn_cover.jpg'
),
(
    N'Unknown',
    N'The Legend of Saint Gióng',
    1,
    N'(Vietnamese folklore, passed down through oral tradition)
	The Legend of Thánh Gióng is a Vietnamese folktale set during the reign of the Hùng Kings. An elderly woman, living alone, miraculously becomes pregnant after stepping into a giant footprint in her field. She gives birth to a boy named Gióng, who remains silent and immobile until age three. When the kingdom faces invasion by the ferocious Ân forces, Gióng suddenly speaks, requesting an iron horse, sword, armor, and helmet to fight the enemy. The king complies, and Gióng rapidly grows into a giant, consuming vast amounts of food and outgrowing clothes as villagers support him. Equipped with the iron gear, Gióng becomes a divine warrior, riding his fire-breathing iron horse to defeat the Ân invaders in a swift, devastating battle. After breaking his sword, he uses bamboo to finish the enemy, slaying their leader, Ân Vương. Having saved the kingdom, Gióng ascends to the heavens at Sóc Sơn Mountain, leaving his armor behind. The Hùng King honors him as Phù Đổng Thiên Vương, and his legacy endures in landmarks like ponds (believed to be his horse’s hoofprints), Làng Cháy (Burnt Village), and golden bamboo. The tale symbolizes heroism, divine intervention, and national pride in Vietnamese culture.',
    N'https://example.com/thanh_giong_cover.jpg'
),
(
    N'InGlorious',
    N'The Epic of Leviathan (A Mutant''s Ascension)',
    0,
    N'A/N: First of all I want to declare that English is not my first language, so if you cannot overlook a few mistakes then this fanfic is not for you. There is also a neutral evil MC tag so don''t complain on a later date saying that MC is evil or something like that. Last warning, in this fanfic some characters will have a different attitude than the canon or the original version so please note this warning before jumping into this. I am also writing this to pass my time nothing serious, but I will try my best to complete this fic. This is going to be a World Travel Fic. 1st World: My Hero Academia 2nd World: Harry Potter World 3rd World: Marvel Cinematic & X-Men Cinematic Universe X-Over (Comics Elements Involved) 4th World: Bleach & Food WARS X-Over (Slightly AU) Currently, I don''t have a fixed schedule for the updates. So, I will be updating chapters whenever I get time.',
    N'https://example.com/epic_of_leviathan_cover.jpg'
),
(
    N'Chocolion',
    N'The Legendary Mechanic',
    0,
    N'What do you do when you wake up and find yourself inside the very game that you love? What do you do when you realize you that you have not only become an NPC – you have even been thrown back in time to before the game even launched! What will happen when our protagonist’s two realities coincide? Han Xiao was a professional power leveler before his transmigration. Using his past life’s knowledge, Han Xiao sweeps through the universe as he prepares for the arrival of the players. This is definitely not your typical transmigration novel.',
    N'https://example.com/legendary_mechanic_cover.jpg'
),
(
    N'Exlor',
    N'The Mech Touch',
    0,
    N'Humanity has conquered half the galaxy and the Age of Mechs reigns supreme! Ves Larkinson lacked the genetic aptitude to become a famed mech pilot. Fighting against his fate, he studied mech design in order to express his love for mechs as a builder instead of a soldier. When Ves graduated from college, he returned to a new but empty boutique. Left with a small, newly founded mech workshop that his father painstakingly built with a mountain of debt, Ves somehow needs to make ends meet with the bank breathing down his neck. In the midst of his despair, he found salvation from another legacy his father had left. "Welcome to the Mech Designer System. Please design your new mech."',
    N'https://example.com/the_mech_touch_cover.jpg'
),
(
    N'Mishima Yomu',
    N'The World of Otome Games is Tough For Mobs',
    0,
    N'Leon, a former Japanese worker, was reincarnated into an “otome game” world, and despaired at how it was a world where females hold dominance over males. It was as if men were just livestock that served as stepping stones for females in this world. The only exceptions were the game’s capture targets, a group of ikemen led by the crown prince. In these bizarre circumstances, Leon held one weapon. He has knowledge from his previous world, where his brazen sister had forced him to complete this game. Leon, who really just wants to to live as a shut-in in the countryside, uses that knowledge to unexpectedly raise a ferocious revolt against the women and ikemen. The curtains begin for an exhilarating fantasy about overtaking the fiendish heroine.',
    N'https://example.com/otome_games_tough_for_mobs_cover.jpg'
);

INSERT INTO [dbo].[Categories] (
    [genre_id],
    [series_id]
)
VALUES
    -- Mai An Tiem (The Legend of Mai An Tiem) - series_id: 1
    (26, 1), -- Mythology
    (8, 1),  -- Folklore
    (16, 1), -- Legend

    -- The Clever Boy - series_id: 2
    (26, 2), -- Mythology
    (8, 2),  -- Folklore

    -- Monarch of Time - series_id: 3
    (22, 3), -- Martial Arts
    (1, 3),  -- Action
    (2, 3),  -- Adventure
    (29, 3), -- Romance
    (11, 3), -- Harem

    -- Shadow Slave - series_id: 4
    (7, 4),  -- Fantasy
    (1, 4),  -- Action
    (2, 4),  -- Adventure
    (29, 4), -- Romance

    -- The Tale of Coconut Skull - series_id: 5
    (26, 5), -- Mythology
    (8, 5),  -- Folklore

    -- Dungeon Diver: Stealing A Monster’s Power - series_id: 6
    (7, 6),  -- Fantasy
    (1, 6),  -- Action
    (2, 6),  -- Adventure

    -- Embers Ad Infinitum - series_id: 7
    (7, 7),  -- Fantasy
    (31, 7), -- Sci-fi
    (32, 7), -- Seinen
    (23, 7), -- Mature
    (39, 7), -- Supernatural

    -- Evolving infinitely from ground zero - series_id: 8
    (1, 8),  -- Action
    (2, 8),  -- Adventure
    (6, 8),  -- Eastern
    (7, 8),  -- Fantasy
    (22, 8), -- Martial Arts

    -- Goddess Medical Doctor - series_id: 9
    (5, 9),  -- Drama
    (29, 9), -- Romance
    (33, 9), -- Shoujo

    -- Harry Potter and the Sorcerer’s Stone - series_id: 10
    (30, 10), -- School Life
    (25, 10), -- Mystery
    (7, 10),  -- Fantasy
    (2, 10),  -- Adventure
    (17, 10), -- Magic

    -- Infinite Mana In The Apocalypse - series_id: 11
    (2, 11),  -- Adventure
    (7, 11),  -- Fantasy
    (9, 11),  -- Game
    (1, 11),  -- Action

    -- The Legend of Sơn Tinh and Thủy Tinh - series_id: 12
    (26, 12), -- Mythology
    (8, 12),  -- Folklore

    -- Supreme Magus - series_id: 13
    (7, 13),  -- Fantasy

    -- Tensei Shitara Slime Datta Ken (WN) - series_id: 14
    (4, 14),  -- Comedy
    (7, 14),  -- Fantasy
    (1, 14),  -- Action
    (2, 14),  -- Adventure
    (35, 14), -- Shounen

    -- Thánh Gióng (The Legend of Saint Gióng) - series_id: 15
    (26, 15), -- Mythology
    (8, 15),  -- Folklore

    -- The Epic of Leviathan (A Mutant's Ascension) - series_id: 16
    (1, 16),  -- Action
    (2, 16),  -- Adventure
    (4, 16),  -- Comedy
    (11, 16), -- Harem
    (29, 16), -- Romance
    (37, 16), -- Slice of Life

    -- The Legendary Mechanic - series_id: 17
    (1, 17),  -- Action
    (2, 17),  -- Adventure
    (24, 17), -- Mecha
    (31, 17), -- Sci-fi

    -- The Mech Touch - series_id: 18
    (31, 18), -- Sci-fi
    (7, 18),  -- Fantasy

    -- The World of Otome Games is Tough For Mobs - series_id: 19
    (11, 19), -- Harem
    (7, 19),  -- Fantasy
    (30, 19), -- School Life
    (4, 19),  -- Comedy
    (31, 19), -- Sci-fi
    (5, 19),  -- Drama
    (24, 19), -- Mecha
    (29, 19), -- Romance
    (35, 19), -- Shounen
    (1, 19)   -- Action
;

INSERT INTO [dbo].[Chapters] (
    [series_id],
    [chapter_index],
    [chapter_title],
	[created_at],
    [content]
)
VALUES (
    1,
    1,
    N'Chapter 1',
    '2024-05-01 12:00:00', 
    N'Once upon a time, there was a young man named Mai An Tiêm. He originally came from a distant land far across the southern seas but was later sold into slavery. One day, traders brought him to be sold to Hùng Vương as a slave. Mai An Tiêm was quick-witted and swiftly learned to speak Vietnamese. Moreover, he had an excellent memory, was knowledgeable about many practical matters, and possessed numerous talents. Because of his versatility and diligence, the king grew increasingly fond of him, often keeping Mai by his side. By the time Mai An Tiêm reached thirty-five years old, he was promoted to a close attendant of the king and was granted a private house next to the palace. His wife was the king’s adopted daughter, and together they had a five-year-old son. Their household was well-equipped with servants and lacked neither fine food nor rare treasures. Although Mai’s authority was not extensive, he was greatly respected by those around him. Consequently, many people frequently visited him to seek favors or assistance. However, his high status also attracted envy and jealousy from others.
One day, during a banquet to entertain court officials, while others competed in flattery and praise, Mai An Tiêm humbly remarked, “It’s nothing special! Everything in this house is merely the result of my past life’s deeds!” He spoke naturally, reflecting the beliefs of his homeland’s religion, which held that one’s current fortunes or hardships were the result of good or bad actions in a previous life. However, among the guests were several of the king’s close attendants who harbored long-standing jealousy toward Mai. Seizing his words, they interpreted them as arrogance and hurriedly reported the matter to the king. Upon hearing this, Hùng Vương was furious, roaring, “How dare he! If he can speak so arrogantly today, who knows what disrespectful words he’ll utter tomorrow? Truly a treacherous slave! Seize him at once!” That very afternoon, soldiers came to Mai’s house, arrested him, and threw him into prison. Only then did Mai realize that his careless words had angered the king. He reflected, “If I am to suffer exile from now on, it must be due to improper conduct in my past life.”
Meanwhile, at court, all the officials were summoned to discuss how to deal with Mai An Tiêm. Some proposed executing him, while others suggested cutting his heels. However, one elderly official offered a suggestion that caught Hùng Vương’s attention: “He deserves death, but before he dies, we should make him fully realize that all the wealth he enjoyed was bestowed by the grace of heaven and the king, not from his so-called ‘past life.’ I’ve heard there’s a deserted island outside Nga Sơn. Let us send him there with one or two months’ worth of provisions, so he can reflect deeply on his ‘past life’ before facing death.” The official’s suggestion was widely supported, and Hùng Vương heartily approved. However, after issuing the order, the king added, “Prepare enough provisions for him to last a season, remember that.”
When Mai An Tiêm was exiled to the deserted island, despite everyone’s attempts to dissuade her, his wife insisted on packing her belongings and following her husband, bringing along their five-year-old son. No one approved of her decision, deeming it utterly foolish and reckless. Yet she firmly believed in her husband’s words: “Heaven creates elephants and also provides grass. There’s nothing to worry about!” However, upon setting foot on the desolate, endless sandy shore, the young woman could not suppress her feelings of despair and wept on her husband’s shoulder, saying, “We might die here!” Mai, remaining calm, held his son and reassured his wife, “Heaven always watches over us. We must stay strong and not worry!”
More than a month after arriving on the island, the family’s life began to stabilize. They lived in rock crevices, with woven screens at the cave entrances to shield against the elements. Water was no issue, as a nearby stream provided a steady supply. Though they lacked salt, seawater served as a substitute. However, their most pressing concern was how to sustain their family’s survival. Looking at their dwindling rice supply, the couple lamented, “If only we had a handful of seeds, we wouldn’t have to worry anymore.”
One day, a large flock of birds flew in from the west, covering the sandy shore in a dark mass. They landed near Mai and his wife’s shelter, squawking loudly, and dropped five or six dark seeds onto the ground. Soon after, these seeds sprouted, growing into vines that spread across the shore, transforming the white sand into a lush green expanse. As the vines spread, they bore vibrant green fruits. Some time later, when Mai and his wife inspected them, the once-small fruits had grown enormous, with smooth green skins and sizes as large as a human head. Curious, Mai picked one and cut it open, revealing a bright red interior speckled with glossy black seeds. The family tasted it and found its sweet, refreshing flavor delightful, cooling their insides. Overjoyed, Mai exclaimed, “My goodness! This is a strange melon we’ve never seen before. Let’s call it ‘Western melon,’ since these seeds were brought by birds from the west, from the mainland to this small island. Heaven has eyes and has given us a way to survive!”
From that day, Mai and his wife worked tirelessly to plant more of these melon seeds across the shore. They planned to eat the melons in place of rice, preserving their limited rice supply. One day, a fishing boat, lost at sea, stumbled upon the island and encountered Mai’s family. Mai helped repair their damaged boat and shared the melons with them. As they set sail back to the mainland, he gave them some melons to share with others, instructing them to bring rice to the island in exchange for more melons. A few days later, the first boat anchored at the island, bringing a substantial amount of white rice. As agreed, they received rice while the sailors took melons back. From then on, the family’s daily meals changed completely. Sitting beside a pot of steaming white rice, Mai’s wife, holding their son, murmured, “Indeed, heaven has sustained our family!”
From that day forward, the couple worked even harder to grow more melons. As a result, not only merchant boats but also fishing boats flocked to the island, bringing rice, clothes, pigs, chickens, knives, axes, and various seeds, all to trade for the melons. Everyone who visited the island told Mai, “Truly, there has never been a melon as precious as this. In our region, everyone dreams of tasting this ‘watermelon,’ and no amount of rice traded for it feels like a loss.” Soon, people competed to visit the island to buy melons and obtain seeds to plant elsewhere. Within a few years, the melon spread far and wide. Thanks to this, Mai An Tiêm and his wife’s reputation grew, earning them the title “Father and Mother of the Western Melon.”
As for Hùng Vương, one day, while scolding an official for incompetence in overseeing a poorly constructed building, he sighed, “If only Mai An Tiêm were here, it wouldn’t have come to this.” That day, the king repeatedly mentioned Mai and asked his Lạc Hầu twice about Mai’s whereabouts and current situation. Unsure of the truth, the Lạc Hầu carelessly said, “He’s probably long gone by now!” The king, unconvinced, immediately provided provisions and a boat to one of his slaves, ordering him to travel to Châu Ái to find Mai An Tiêm. After a month’s journey, the slave returned with a boat full of large Western melons and reported, “These are gifts from Lord and Lady Mai for Your Majesty.” He recounted the hardships Mai’s family faced upon arriving at the island and their current situation, adding, “Now, Lord and Lady Mai have a beautiful, spacious house on the island, with over ten servants, a vast melon field, rice paddies, and many pigs and chickens…” The more he spoke, the more astonished Hùng Vương became. Clicking his tongue, the king said to the attendants who had accused Mai, “He said everything came from his past life, and he wasn’t wrong!”
The king promptly sent a group of attendants to the island to bring Mai An Tiêm and his family back, restoring his former position. He also granted Mai two maidens to serve him as compensation for past wrongs. The island where Mai’s family had lived is now called An Tiêm Beach. The descendants of Mai and his wife, who remained on the island, grew numerous and established Mai An Village. At the site of their former home, a temple was built to honor Mai An Tiêm and his wife, who are revered as the “Ancestors of the Western Melon (also known as watermelon).”
Explanations of Key Vietnamese Terms
Nô:
Literal meaning: Slave or servant.
Contextual meaning: Refers to Mai An Tiêm’s initial status as a purchased slave, highlighting his lowly origins before rising to prominence.
Quan hầu cận:
Literal meaning: Close attendant or courtier.
Contextual meaning: A high-ranking position near the king, indicating Mai’s trusted role despite his modest authority.
Người hầu kẻ hạ:
Literal meaning: Servants and attendants.
Contextual meaning: Refers to the household staff serving Mai’s family, emphasizing their comfortable lifestyle before exile.
Vật tiền thân:
Literal meaning: “Things of the past life” or “past life’s possessions.”
Contextual meaning: Reflects Mai’s belief, rooted in his homeland’s religion, that his current circumstances are the result of actions in a previous life, which is misinterpreted as arrogance by others.
Cầu cạnh:
Literal meaning: To seek favors or curry favor.
Contextual meaning: Describes how people approached Mai for assistance due to his influence, highlighting his respected status.
Cắt gót chân:
Literal meaning: Cutting the heels.
Contextual meaning: A severe punishment proposed for Mai, indicating the harsh penalties considered during his trial.
Trời sinh voi rồi lại sinh cỏ:
Literal meaning: “Heaven creates elephants and also provides grass.”
Contextual meaning: A proverb expressing faith that nature or divine forces will provide for one’s needs, reflecting Mai’s optimism.
Dưa Tây:
Literal meaning: “Western melon.”
Contextual meaning: Refers to the watermelon, named for the seeds brought by birds from the west, symbolizing divine providence in the story.
Bố cái dưa Tây:
Literal meaning: “Father of the Western melon.”
Contextual meaning: An honorary title given to Mai and his wife for introducing the watermelon, signifying their legacy.
Bãi An Tiêm:
Literal meaning: An Tiêm Beach.
Contextual meaning: The island where Mai’s family lived, named after him, symbolizing his enduring impact on the region.'
),
(
    2,
    1,
    N'Chapter 1',
    '2024-05-01 10:30:00',
    N'Once upon a time, when our country was in need of talented individuals to help govern, the court officials were aging and lacked vigor. The king sent an official to travel across the land to seek out a wise and capable person to assist in state affairs. The official journeyed far and wide, wearing out his horse, but found no one satisfactory. Wherever he went, he posed challenging riddles to identify talent, yet no one could solve them. One day, passing through a village field, exhausted and resting by the roadside while his horse grazed, he saw a father and son working the land. The father, frail and thin, was guiding a buffalo to plow, while his son, about seven or eight years old, was breaking up clumps of soil. The official asked, “Hey, old man! How many furrows can your buffalo plow in a day?” The father, puzzled and unsure how to respond to the official, hesitated. But the boy quickly answered, “Sir, let me ask you first: if you can tell me how many steps your horse takes in a day, I’ll tell you how many furrows our buffalo plows.” The official was astonished by the boy’s clever response, momentarily at a loss for words, but inwardly delighted, thinking, “This boy will surely grow up to be a great talent. Why bother searching further?”
The official inquired about the father and son’s names and hometown, then galloped back to report to the king. Seeing the official return enthusiastically and claim he had found a talent, the king was pleased but wanted to test the boy’s intelligence. He sent messengers to the village with three bushels of sticky rice and three male buffalo, ordering the village to raise the buffalo to produce nine offspring by the next year or face punishment. The villagers, upon receiving the king’s decree, were both hopeful and anxious—hopeful because the king’s attention might bring future support, but worried because male buffalo cannot bear offspring. Numerous village meetings yielded no solutions, and the decree was seen as an impending disaster. When the boy, son of the plowman, heard of this, he told his father, “Father, it’s rare to receive the king’s favor. Tell the village to slaughter two buffalo and cook two bushels of sticky rice for everyone to feast heartily. With the remaining buffalo and rice, we’ll ask the village to sell them to fund our journey to the capital.” The father, alarmed, replied, “Son, slaughtering the king’s buffalo? Not just a year from now, but tomorrow the whole village could be punished! Don’t be foolish.” The boy smiled confidently and said, “Trust me, Father. I know how to handle this, and it will all work out.”
The father, persuaded, went to the village elders to share the boy’s plan. Skeptical, the villagers demanded a written pledge from the father and son, agreeing to proceed only if they took responsibility. A few days later, the father and son packed and set off for the capital. At the royal palace, the boy told his father to wait outside for good news, then,趁着 guards were distracted, slipped into the courtyard and began crying loudly. Hearing a child’s commotion, the king sent soldiers to bring the boy before him and asked, “You there, why are you making such a racket in my courtyard?” The boy replied, “Your Majesty, my mother died early, and it’s just me and my father. But he won’t give me a sibling to play with, so I’m crying. Please, Your Majesty, order my father to give me a sibling!” The king and his courtiers burst into laughter. The king said, “If you want a sibling, your father must remarry. A man cannot give birth!” The boy, suddenly cheerful, responded, “Then why did Your Majesty order our village to make three male buffalo produce nine offspring? Male buffalo can’t give birth either.” The king laughed, saying, “That was just a test of your wit. Didn’t your village slaughter the buffalo and feast together?” The boy replied, “Your Majesty, upon receiving your buffalo and rice, our village, knowing it was your gift, held a grand feast.”
The king acknowledged that the official’s report was correct—this boy was remarkably clever. However, he wanted to test him further. The next day, while the father and son were eating at a public guesthouse, a royal messenger brought a sparrow and said, “The king orders you to prepare three dishes from this sparrow.” The quick-witted boy told his father to fetch a sewing needle and said to the messenger, “Take this needle to the king and ask him to have it forged into a knife so we can butcher the sparrow.” When the messenger reported this to the king, the king was thoroughly impressed by the boy’s ingenuity. He summoned the father and son to the palace and rewarded them generously.
At that time, a neighboring country, plotting to invade, sent an envoy with a tricky riddle to test for talent: “How can you thread a thin string through a hollow, spiral seashell with openings at both ends?” Upon hearing the envoy’s challenge, the king and his ministers exchanged worried glances. Failing to solve the riddle would signal weakness and a lack of talent, giving the neighboring country an advantage. The ministers tried various methods—sucking the thread through, coating it with wax to stiffen it—but all failed. With no solution, the king, to buy time, invited the envoy to rest at the guesthouse. A royal official was dispatched with the king’s decree to consult the clever boy. The boy, playing with friends behind his house, listened to the riddle and, instead of answering directly, sang a song: “Tang tính tang! Tính tình tang! Catch an ant, tie a thread around its waist. Cover one end with paper, smear grease on the other, the ant will gladly crawl through. Tang tình tang…” He added, “I don’t need to return to court. Follow my song, and the thread will pass through the shell!” The official joyfully returned and reported to the king. Sure enough, an ant threaded the string through the shell, astonishing the foreign envoy. From then on, the king appointed the boy as the top scholar (trạng nguyên) and had a residence built for him near the palace for easy consultation.

Explanations of Key Vietnamese Terms
Người hiền tài:
Literal meaning: Virtuous and talented person.
Contextual meaning: Refers to individuals with exceptional wisdom and ability, sought by the king to aid in governing the country.
Viên quan:
Literal meaning: Official or mandarin.
Contextual meaning: A court official tasked with carrying out the king’s orders, here responsible for finding talent.
Câu đố hóc búa:
Literal meaning: Tricky riddle.
Contextual meaning: A challenging puzzle designed to test intelligence, used by the official to identify talent.
Trâu đực:
Literal meaning: Male buffalo.
Contextual meaning: Emphasizes the impossibility of the king’s task (male buffalo cannot bear offspring), used as a test of wit.
Lộc vua ban:
Literal meaning: Gifts bestowed by the king.
Contextual meaning: Refers to the rice and buffalo, symbolizing royal favor but also a test, which the villagers initially see as a burden.
Đình:
Literal meaning: Village communal house.
Contextual meaning: The central meeting place where village elders gather to discuss important matters, such as the king’s decree.
Giấy cam đoan:
Literal meaning: Written pledge or guarantee.
Contextual meaning: A formal document demanded by the villagers to hold the father and son accountable for their bold plan.
Trẩy kinh:
Literal meaning: Journey to the capital.
Contextual meaning: Refers to traveling to the royal court to address the king directly, a significant undertaking for commoners.
Trạng nguyên:
Literal meaning: Top scholar or first laureate.
Contextual meaning: The highest rank in the imperial examination system, here bestowed on the boy as an honorary title for his exceptional intelligence.
Công quán:
Literal meaning: Public guesthouse.
Contextual meaning: A government-provided lodging for travelers or officials, where the father and son stay and receive the king’s challenges.'
),
-- Series 3: 5 chapter
(
    3,
    1,
    N'Chapter 1',
    '2024-04-15 06:00:00',
    N'The sun slowly rose in the Guangdong province of China.
In a complex of abandoned apartments scheduled for demolition an alarm was ringing at 6:00 am in the morning.
The sound was coming from an almost broken down apartment, a door you couldn''t close properly no matter how hard you pushed, with a chair behind it in a 45 degree angle acting as a lock for the door.
The apartment was medium sized, around 80 s.m. yet it was almost empty inside. A bed mattress was in the middle of the living room along with a small sized table with an alarm clock on it. On the side of the room were 10 big water bottles along with some packs of instant noodles.
A body could be seen moving under the blanket that was covering the mattress, reaching for the alarm clock on the table.
"Ugh" that person groaned after he tapped the alarm clock once.
A malnourished body revealed itself from underneath the blanket as it slowly rose from the bed.
A young man seemingly around 16-17 years old rose from the mattress as he walked towards the bathroom. The water was cut off since the apartment was abandoned so the young man couldn''t even take a shower in the morning. He grabbed a half empty bottle of water from the table with the alarm clock on it, and used it along with his toothbrush to wash his teeth and when he finished, he then gulped down the rest of the water from the bottle. Then he grabbed his work clothes from the table and put them on as he left the apartment.
It was around a 20 minute walk from the apartment complex until he reached a huge construction site with a lot of warning signs of: "Danger" and "Work in progress".
As soon as he walked towards an 8 story half-finished building, the young man heard a voice from behind him shouting "ay Rock". The voice belonged to a 45 year old man.
"Good morning uncle" said Rock.
Of course Rock wasn''t his real name, but a nickname given to him by the people working in the construction site with him as he didn''t like to talk that much. Rock didn''t have a real name from his parents, he was an orphan, and he grew up in a house complex owned by the government for orphaned children. The government sends food to those houses once a week but it wasn''t enough for the kids there to feed themselves. The orphanages were all full and that''s why the government created these houses and sent food to the children but a lot of them still died due to hunger or illnesses as there was no one to take care of them even when they were ill. Rock stayed there until he was 12. He could fight the other children for the food, but even then it was not enough to feed himself so he decided to leave.
He attended junior high-school in a public school not far away from the house he lives. It was school in the morning and construction work in the afternoon.
Usually people in the construction sites didn''t allow kids at work, considering it was illegal but due to the low wages, few people went to work at those construction sites, and since lower manpower equaled slow production, the construction manager accepted even a kid like Rock with skinny arms and legs, and allowed him to work there, for a pitiful amount of money as his wage of course. Rock wasn''t dissatisfied, as he would finally have enough money every month to feed himself at least.
This situation occurred until Rock graduated from high-school but he never stopped working at the construction sites as that was his only way of income.
It would be a lie to say that he didn''t feel aggrieved. Even working for his food in construction sites without proper security measures was considered normal to him nowadays but he still hoped deep inside that his luck would change one day and his life would take a turn for the better.
For the past half a year that Rock has been working on this construction site, this "uncle" has taken care of him plenty of times, either by sharing with him food or drinks, or taking care of helping him during work when needed.
Needless to say that rock felt extremely thankful to this man and he also helped him during work whenever possible as well.
"Haa kid.. I knew you wouldn''t bring breakfast with you again. My wife gave me extra again today so let''s share it together." as soon as he finished his sentence he grabbed a plastic bag and took out 2 big water bottles along with 2 sandwiches and 4 rice balls. He split them at 2 portions and gave one to Rock.
The man knew Rock wasn''t one to accept charity so easily so he said "Hey kid we have to eat up to start working, you still need to take care of me as this job is really dangerous so eat up."
Rock still didn''t respond, until the man spoke again and said: "You will treat me to drink again when you get your wage okay kid? Let''s eat!" then without even looking at Rock again, the man started eating the food.
Rock didn''t say anything but he also started eating, keeping this gesture of this man deep inside his heart. This middle aged man was the only one who had ever shown any form of care for him, in the 20 years of his life and Rock would never forget this kindness.
After they finished their food, they started their daily work but little did they know that this would be the last day of Rock''s life.'
),
(3, 2, N'Chapter 2', '2024-04-16 06:00:00',
 N'Rock and the middle aged man were among the first ones to come to work everyday. The man''s wife was pregnant for 6 months and his child would soon be born, so he had to work hard to make ends meet both for his wife as well as his unborn child.
Time flew and soon, noon arrived.
Rock was about to finish with the 5th floor and take a break for a few minutes to rehydrate himself, since working under the scorching sun for hours was never good, especially with a body as weak as his, when coincidentally the middle aged uncle also stopped for a break. They started descending the half completed stairs that connected the 5th floor to the 4th, and they had to cross atop a wooden plank to reach the 4th floor.
While they were walking atop the plank, a huge rumbling sound and a tremor started to shake the entire building. Who could expect an earthquake at the worst possible moment, especially one of such magnitude that started shaking the entire structure of the building down to its foundations !?
Rock saw the middle aged uncle walking unsteadily to the other side, terrified of the earthquake that was taking place, but before he could get to the other side of the plank, his right foot slipped as he lost his balance!
"NO!"
Just as he was about to fall down and crash, from the 4th floor directly to the ground, Rock rushed from behind and pushed him using all of his strength, causing the middle-aged man to almost fly to the other side, saving his life with that push.
Just as the man landed on the other side, even more intense rumbling came from the gap above Rock''s head. A huge mass of debris buried him to the ground and half of the building itself collapsed. The middle aged man starting to roar and cry and as soon as the rumbling stopped and the opposite side of the building collapsed while the one he was on miraculously stayed intact. He run down as fast as he could and used his bare hands to remove the pieces of rock, glass and concrete that buried Rock.
But it was pointless, the debris was practically a tower at this point, since half of the building had collapsed.... it was already a miracle that the other half stayed standing and didn''t fall along with it.
The middle aged man was still crying, his hands bloodied from the pieces of glass stuck in them but he was still removing the debris hoping to save Rock. He felt so guilty that the kid exhanged his life so he can stay alive. He kept going until the other workers came over and stopped him.
Rock had seen the debris fall on his head and understood exactly how unlucky he was as this was practically certain death, yet he didn''t regret saving the man. The middle aged uncle was the only one who had shown care for him, and the thought that went through his mind as he was being burried by the rocks was "Ah it''s good since uncle lived.. but it''s truly unfair that my life will end like this". The rumbling continued for a while and Rock''s vision turned dark as he blacked out.
Rock tried to slowly open his eyes as his mind started to regain its consciousness, only to discover that his head was about to split apart from a terrifying pain he was feeling. He started wailing left and right as he couldn''t feel anything other than the splitting headache that was about to rip his mind apart.
What Rock hadn''t noticed was that his body wasn''t buried under any rubble or debris, but was lying on the ground with his face up, on a patch of red grass, right in the middle of a huge thunderstorm with thunder and lightning striking the ground all around him.
The bones on both his arms and legs were broken, his head bleeding from behind, and his clothes were tattered and full of blood. There was a huge wound on the left side of his chest probably made from a sword or a saber judging by its size. Luckily for Rock, it was still a few inches away from his heart.
He felt as if something was forcefully being stuffed in his brain, but there was not enough room for it to fit and his brain was about to burst.
Just as a bolt of lightning was about to strike his head and end his life for the second time today, a space tear appeared above him as the lightning was swallowed and disappeared inside it.
At the same time, a stone slowly left the space tear in the air, and as soon as it appeared, heaven and earth suddenly fell silent.
A few miles away, the people of Blue Forest-city had all gone still. Pedestrians were frozen mid-walking, people inside the restaurants were frozen while they were chatting and laughing or eating, stall owners haggling to sell their merchandise were immobilized, and even the fire in the taverns and the food that stall owners used to cook, were all in a frozen-like state.
Bolts of lightning all around Rock were stopped in place like a painting in the air, as if the very heavens themselves were afraid to move the moment that this stone appeared.
The triangle-shaped stone released a blindingly bright azure light that destroyed the black clouds and the bolts of lightning frozen mid air around Rock, along with everything else in a radius of one mile of Rock.
Rock suddenly felt the change in the environment around him and finally opened his eyes, forcibly suppressing the head splitting pain he was feeling which had slowly started to ease, only to see a blue like light from a triangle shaped-like stone right above the space between his eyebrows, plunging itself right inside his head.
'),
(3, 3, N'Chapter 3', '2024-04-17 06:00:00',
 N'...'),
(3, 4, N'Chapter 4', '2024-04-18 06:00:00',
 N'...'),
(3, 5, N'Chapter 5', '2024-04-19 06:00:00',
 N'...'),
 -- Series 4: 5 chapter
(4, 1, N'Chapter 1', '2024-05-01 08:00:00', 
N'A frail-looking young man with pale skin and dark circles under his eyes was sitting on a rusty bench across from the police station. He was cradling a cup of coffee in his hands — not the cheap synthetic type slum rats like him had access to, but the real deal. This cup of plant-based coffee, usually available only to higher rank citizens, had cost most of his savings. But on this particular day, Sunny decided to pamper himself.
After all, his life was coming to an end.
Enjoying the warmth of the luxurious drink, he raised the cup and savored the aroma. Then, tentatively, he took a small sip… and immediately grimaced.
“Ah! So bitter!”
Giving the cup of coffee an intense look, Sunny sighed and forced himself to drink some more. Bitter or not, he was determined to get his money''s worth — taste buds be damned.
“I should have bought a piece of real meat instead. Who knew actual coffee is so disgusting? Well. It''s going to keep me awake, at least.”
He stared into the distance, dozing off, and then slapped himself in the face to wake up.
“Tsk. What a rip-off.”
Shaking his head and cursing, Sunny finished the coffee and stood up. Rich people living in this part of the city were rushing past the small park on their way to work, staring at him with strange expressions. Looking haggard in his cheap clothes and from the lack of sleep, unhealthily thin and pale, Sunny was indeed out of his place here. Also, everyone seemed so tall. Watching them with a bit of envy, he tossed the cup into a garbage bin.
“I guess that''s what three full meals a day would do to you.”
The cup missed the bin by a wide margin and fell on the ground. Sunny rolled his eyes in exasperation, walked over and picked it up before carefully putting it in the trash. Then, with a slight grin, he crossed the street and entered the police station.
Inside, a tired-looking officer gave him a quick glance and frowned with obvious distaste.
“Are you lost, boy?”
Sunny looked around with curiosity, noting reinforced armor plates on the walls and poorly hidden turret nests in the ceiling. The officer, too, looked scruffy and mean. At least police stations remained the same wherever you go.
“Hey! I''m talking to you!”
Sunny cleared his throat.
“Uh, no.”
Then he scratched the back of his head and added:
“As demanded by the Third Special Directive, I am here to surrender myself as a carrier of the Nightmare Spell.”
The officer''s expression instantly changed from irritated to wary. He looked the young man over once again, this time with piercing intensity.
“Are you sure you are infected? When did you start showing symptoms?”
Sunny shrugged.
“A week ago?”
The officer became visibly paler.
“Shit.”
Then, with a hurried motion, he pressed a button on his terminal and bellowed:
“Attention! Code Black in the lobby! I repeat! CODE BLACK!”
***
The Nightmare Spell first appeared in the world a few decades ago. Back then, the planet was just starting to recover from a series of devastating natural disasters and subsequent resource wars.
At first, the emergence of a new disease that caused millions of people to complain about constant fatigue and sleepiness did not attract a lot of attention. But when they started to fall into an unnatural slumber, with no sign of waking up even days later, governments finally panicked. Of course, by then it was already too late — not that an early response could have made any difference.
When the infected started dying in their sleep, their dead bodies turning into monsters, no one was ready. Nightmare Creatures quickly overwhelmed national militaries, plunging the world into complete chaos.
No one knew what the Spell was, what powers it possessed, and how to fight it.
In the end, it was the Awakened — those who survived the first trials of the Spell and came back alive — who put a stop to its rampage. Armed with miraculous abilities earned in their Nightmares, they restored peace and created a semblance of a new order.
Of course, it was only the first of the catastrophes brought upon by the Spell. But as far as Sunny was concerned, none of it had anything to do with him — not until a few days ago, that is, when he first started having trouble with staying awake.
For an average person, being chosen by the Spell was as much of a risk as an opportunity. Kids learned survival skills and fighting techniques in school, on the off chance of being infected. Well-to-do families hired private tutors to train their children in all sorts of martial arts. Those from the Awakened clans even had access to powerful legacies, wielding inherited Memories and Echoes in their first visit to the Dream Realm.
The richer your family was, the better your chances of surviving and becoming an Awakened were.
But for Sunny, who had no family to speak of and spent most of his time scrounging for food instead of going to school, being chosen by the Spell presented no opportunity at all. To him, it was basically a death sentence.
***
A few minutes later, Sunny was yawning while several policemen were busy putting him in restraints. Soon he was fastened into a bulky chair that looked like a weird mix between a hospital bed and a torture device. The room they were in was situated in the basement of the police station, with thick armored walls and a formidable-looking vault door. Other officers were standing near the walls, with automatic rifles in their hands and grim expressions on their faces.
Sunny did not particularly care about them. The only thing he could think about was how much he wanted to sleep.
Finally, the vault door opened, and a gray-haired policeman walked in. He had a seasoned face and stern eyes, looking like someone who had seen a lot of terrible things in his life. After checking the restraints, the policeman glanced quickly on his wristwatch and then turned to Sunny:
“What''s your name, kid?”
Sunny blinked a few times, trying to concentrate, then shifted uncomfortably.
“Sunless.”
The old policeman raised an eyebrow.
“Sunless? That''s a strange name.”
Sunny tried to shrug, but found himself unable to move.
“What''s so strange about it? At least I have a name. Back in the outskirts, not everyone even gets one.”
After another yawn, he added:
“It''s because I was born during a solar eclipse. My mom had a poetic soul, you see.”
That''s why he got this weird-ass name and his little sister was called Rain… back when she still lived with them, at least. Whether it was the result of poetic imagination or simple laziness, he did not know.
The old policeman grunted.
“Do you want me to contact your family?”
Sunny simply shook his head.
“There''s no one. Don''t bother.”
For a second, there was a dark look on the policeman''s face. Then his expression turned serious.
“Alright, Sunless. How long can you stay awake?”
“Uh… not long.”
The policeman sighed.
“Then we don''t have time for the full procedure. Try to resist for as long as you can and listen to me very carefully. Okay?”
Not waiting for a response, he added:
“How much do you know about the Nightmare Spell?”
Sunny gave him a questioning look.
“As much as anyone, I guess? Who doesn''t know about the Spell?”
“Not the fancy stuff you see in dramas and hear in the propaganda broadcasts. I mean how much do you really know?”
That was a hard question to answer.
“Don''t I just go into the Dream Realm, kill a few monsters to complete the First Nightmare, receive magic powers and become an Awakened?”
The old policeman shook his head.
“Listen carefully. Once you fall asleep, you will be transported inside your First Nightmare. Nightmares are trials created by the Spell. Once inside, you will meet monsters, sure, but you will also meet people. Remember: they are not real. They''re just illusions conjured up to test you.”
“How do you know?”
The policeman just stared at him.
“I mean, no one understands what the Spell is and how it works, right? So how do you know that they''re not real?”
“You might have to kill them, kid. So do yourself a favor and just think about them as illusions.”
“Oh.”
The old policeman waited for a second, then nodded and continued.
“A lot of things about the First Nightmare depend on luck. Generally, it shouldn''t be overwhelmingly hard. The situation you''re in, the tools you have at your disposal and the creatures you have to defeat should be within the range of your abilities, at least. After all, the Spell sets up trials, not executions. You''re a bit disadvantaged due to… well… your circumstances. But kids from the outskirts are tough. Don''t give up on yourself just yet.”
“Uh-uh.”
Sunny was getting more and more sleepy. It was becoming hard to follow the conversation.
“About those “magic powers” you mentioned… you will indeed receive them if you survive until the end of the Nightmare. What those powers will be, exactly, depends on your natural affinity as well what you do during the trial. But some of it will be at your disposal right from the start…”
The voice of the old policeman sounded more and more distant. Sunny''s eyelids were so heavy that he was struggling to keep his eyes open.
“Remember: the first thing you must do once inside the Nightmare is to check your Attributes and your Aspect. If you get a combat-oriented Aspect, something like a Swordsman or an Archer, things will be easier. If it is reinforced by a physical Attribute, then that''s even better. Combat Aspects are the most common, so the probability of receiving one is high.”
The armored room was growing dimmer.
“If you''re unlucky and your Aspect has nothing to do with combat, don''t despair. Sorcery and utility Aspects are useful in their own ways, you''ll just have to be smart about it. There are really no useless Aspects. Well, almost. So just do anything in your power to survive.”
“If you survive, you will be halfway to becoming an Awakened. But if you die, you''ll open a gate for a Nightmare Creature to appear in the real world. Which means that my colleagues and I will have to deal with it. So… please don''t die, Sunless.”
Already half-asleep, Sunny felt a bit touched by the policeman''s words.
“Or, at least, try to not die right away. The nearest Awakened won''t be able to get here for a few hours, so we would really appreciate it if you don''t make us fight that thing ourselves…”
''What?''
With that last thought, Sunny finally slipped into a deep slumber.
Everything became black.
And then, in the darkness, a faintly familiar voice rang:
[Aspirant! Welcome to the Nightmare Spell. Prepare for your First Trial…]
'),
(4, 2, N'Chapter 2', '2024-05-02 08:00:00', 
N'...'),
(4, 3, N'Chapter 3', '2024-05-03 08:00:00', 
N'...'),
(4, 4, N'Chapter 4', '2024-05-04 08:00:00', 
N'...'),
(4, 5, N'Chapter 5', '2024-05-05 08:00:00', 
N'...'),
-- Series 5: 1 chapter
(5, 1, N'Chapter 1', '2024-05-06 08:00:00', N'...'),

-- Series 6: 7 chapters
(6, 1, N'Chapter 1', '2024-05-07 08:00:00', N'...'),
(6, 2, N'Chapter 2', '2024-05-08 08:00:00', N'...'),
(6, 3, N'Chapter 3', '2024-05-09 08:00:00', N'...'),
(6, 4, N'Chapter 4', '2024-05-10 08:00:00', N'...'),
(6, 5, N'Chapter 5', '2024-05-11 08:00:00', N'...'),
(6, 6, N'Chapter 6', '2024-05-12 08:00:00', N'...'),
(6, 7, N'Chapter 7', '2024-05-13 08:00:00', N'...'),

-- Series 7: 50 chapters
(7, 1, N'Chapter 1', '2024-05-14 08:00:00', N'...'),
(7, 2, N'Chapter 2', '2024-05-15 08:00:00', N'...'),
(7, 3, N'Chapter 3', '2024-05-16 08:00:00', N'...'),
(7, 4, N'Chapter 4', '2024-05-17 08:00:00', N'...'),
(7, 5, N'Chapter 5', '2024-05-18 08:00:00', N'...'),
(7, 6, N'Chapter 6', '2024-05-19 08:00:00', N'...'),
(7, 7, N'Chapter 7', '2024-05-20 08:00:00', N'...'),
(7, 8, N'Chapter 8', '2024-05-21 08:00:00', N'...'),
(7, 9, N'Chapter 9', '2024-05-22 08:00:00', N'...'),
(7, 10, N'Chapter 10', '2024-05-23 08:00:00', N'...'),
(7, 11, N'Chapter 11', '2024-05-24 08:00:00', N'...'),
(7, 12, N'Chapter 12', '2024-05-25 08:00:00', N'...'),
(7, 13, N'Chapter 13', '2024-05-26 08:00:00', N'...'),
(7, 14, N'Chapter 14', '2024-05-27 08:00:00', N'...'),
(7, 15, N'Chapter 15', '2024-05-28 08:00:00', N'...'),
(7, 16, N'Chapter 16', '2024-05-29 08:00:00', N'...'),
(7, 17, N'Chapter 17', '2024-05-30 08:00:00', N'...'),
(7, 18, N'Chapter 18', '2024-05-31 08:00:00', N'...'),
(7, 19, N'Chapter 19', '2024-06-01 08:00:00', N'...'),
(7, 20, N'Chapter 20', '2024-06-02 08:00:00', N'...'),
(7, 21, N'Chapter 21', '2024-06-03 08:00:00', N'...'),
(7, 22, N'Chapter 22', '2024-06-04 08:00:00', N'...'),
(7, 23, N'Chapter 23', '2024-06-05 08:00:00', N'...'),
(7, 24, N'Chapter 24', '2024-06-06 08:00:00', N'...'),
(7, 25, N'Chapter 25', '2024-06-07 08:00:00', N'...'),
(7, 26, N'Chapter 26', '2024-06-08 08:00:00', N'...'),
(7, 27, N'Chapter 27', '2024-06-09 08:00:00', N'...'),
(7, 28, N'Chapter 28', '2024-06-10 08:00:00', N'...'),
(7, 29, N'Chapter 29', '2024-06-11 08:00:00', N'...'),
(7, 30, N'Chapter 30', '2024-06-12 08:00:00', N'...'),
(7, 31, N'Chapter 31', '2024-06-13 08:00:00', N'...'),
(7, 32, N'Chapter 32', '2024-06-14 08:00:00', N'...'),
(7, 33, N'Chapter 33', '2024-06-15 08:00:00', N'...'),
(7, 34, N'Chapter 34', '2024-06-16 08:00:00', N'...'),
(7, 35, N'Chapter 35', '2024-06-17 08:00:00', N'...'),
(7, 36, N'Chapter 36', '2024-06-18 08:00:00', N'...'),
(7, 37, N'Chapter 37', '2024-06-19 08:00:00', N'...'),
(7, 38, N'Chapter 38', '2024-06-20 08:00:00', N'...'),
(7, 39, N'Chapter 39', '2024-06-21 08:00:00', N'...'),
(7, 40, N'Chapter 40', '2024-06-22 08:00:00', N'...'),
(7, 41, N'Chapter 41', '2024-06-23 08:00:00', N'...'),
(7, 42, N'Chapter 42', '2024-06-24 08:00:00', N'...'),
(7, 43, N'Chapter 43', '2024-06-25 08:00:00', N'...'),
(7, 44, N'Chapter 44', '2024-06-26 08:00:00', N'...'),
(7, 45, N'Chapter 45', '2024-06-27 08:00:00', N'...'),
(7, 46, N'Chapter 46', '2024-06-28 08:00:00', N'...'),
(7, 47, N'Chapter 47', '2024-06-29 08:00:00', N'...'),
(7, 48, N'Chapter 48', '2024-06-30 08:00:00', N'...'),
(7, 49, N'Chapter 49', '2024-07-01 08:00:00', N'...'),
(7, 50, N'Chapter 50', '2024-07-02 08:00:00', N'...'),

-- Series 8: 4 chapters
(8, 1, N'Chapter 1', '2024-07-03 08:00:00', N'...'),
(8, 2, N'Chapter 2', '2024-07-04 08:00:00', N'...'),
(8, 3, N'Chapter 3', '2024-07-05 08:00:00', N'...'),
(8, 4, N'Chapter 4', '2024-07-06 08:00:00', N'...'),

-- Series 9: 14 chapters
(9, 1, N'Chapter 1', '2024-07-07 08:00:00', N'...'),
(9, 2, N'Chapter 1', '2024-07-07 08:00:00', N'...'),
(9, 3, N'Chapter 1', '2024-07-07 08:00:00', N'...'),
(9, 4, N'Chapter 1', '2024-07-07 08:00:00', N'...'),
(9, 5, N'Chapter 1', '2024-07-07 08:00:00', N'...'),
(9, 6, N'Chapter 1', '2024-07-07 08:00:00', N'...'),
(9, 7, N'Chapter 1', '2024-07-07 08:00:00', N'...'),
(9, 8, N'Chapter 1', '2024-07-07 08:00:00', N'...'),
(9, 9, N'Chapter 1', '2024-07-07 08:00:00', N'...'),
(9, 10, N'Chapter 1', '2024-07-07 08:00:00', N'...'),
(9, 11, N'Chapter 1', '2024-07-07 08:00:00', N'...'),
(9, 12, N'Chapter 1', '2024-07-07 08:00:00', N'...'),
(9, 13, N'Chapter 1', '2024-07-07 08:00:00', N'...'),
(9, 14, N'Chapter 14', '2024-07-20 08:00:00', N'...'),

-- Series 10: 17 chapters
(10, 1, N'Chapter 1', '2024-07-21 08:00:00', N'...'),
-- Series 11 - 19
(11, 1, N'Chapter 1', '2024-07-03 08:00:00', N'...'),
(12, 1, N'Chapter 1', '2024-07-04 08:00:00', N'...'),
(13, 1, N'Chapter 1', '2024-07-05 08:00:00', N'...'),
(14, 1, N'Chapter 1', '2024-07-06 08:00:00', N'...'),
(15, 1, N'Chapter 1', '2024-07-07 08:00:00', N'...'),
(16, 1, N'Chapter 1', '2024-07-08 08:00:00', N'...'),
(17, 1, N'Chapter 1', '2024-07-09 08:00:00', N'...'),
(18, 1, N'Chapter 1', '2024-07-10 08:00:00', N'...'),
(19, 1, N'Chapter 1', '2024-07-11 08:00:00', N'...');


INSERT INTO [dbo].[UserLibraries] (
    [user_id],
    [series_id]
)
VALUES
(2, 1),
(3, 2),
(4, 3),
(5, 4),
(2, 5),
(3, 6),
(4, 7),
(5, 8),
(2, 9),
(3, 10);

INSERT INTO [dbo].[HistoryReading] (
    [user_id],
    [series_id],
    [chapter_id]
)
VALUES 
(2, 1, 1),
(3, 2, 1),
(4, 3, 5),
(5, 4, 3),
(2, 5, 1),
(3, 6, 7),
(4, 11, 8),
(5, 12, 1),
(4, 19, 1);