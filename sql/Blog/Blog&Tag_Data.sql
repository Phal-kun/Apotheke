-- Insert sample data into the tag table
INSERT INTO [tag] (tagName) VALUES ('Health');
INSERT INTO [tag] (tagName) VALUES ('Pharmacy');
INSERT INTO [tag] (tagName) VALUES ('Medicine');
INSERT INTO [tag] (tagName) VALUES ('Wellness');
GO

-- Insert sample data into the blog table
INSERT INTO [blog] (title, content, publicDate, userID, status) 
VALUES ('Importance of Health', 'Health is the most valuable asset...', '2023-09-21', 1, 1);
INSERT INTO [blog] (title, content, publicDate, userID, status) 
VALUES ('Pharmacy Online Shopping Guide', 'Pharmacy shopping made easy...', '2023-09-22', 2, 1);
INSERT INTO [blog] (title, content, publicDate, userID, status) 
VALUES ('Top Medicines for Flu', 'Best medicines to take during flu season...', '2023-09-23', 3, 1);
GO

-- Insert sample data into the blogTag table
INSERT INTO [blogTag] (blogID, tagID) VALUES (7, 1); -- Blog 1 with tag 'Health'
INSERT INTO [blogTag] (blogID, tagID) VALUES (7, 4); -- Blog 1 with tag 'Wellness'
INSERT INTO [blogTag] (blogID, tagID) VALUES (8, 2); -- Blog 2 with tag 'Pharmacy'
INSERT INTO [blogTag] (blogID, tagID) VALUES (9, 3); -- Blog 3 with tag 'Medicine'
GO

-- Insert 10 more blogs into the blog table
INSERT INTO [blog] (title, content, publicDate, userID, status) 
VALUES ('Healthy Eating Habits', 'A guide to maintaining a balanced diet...', '2023-09-24', 1, 1);
INSERT INTO [blog] (title, content, publicDate, userID, status) 
VALUES ('Best Pharmacy Chains in 2024', 'A review of top pharmacy chains...', '2023-09-25', 2, 1);
INSERT INTO [blog] (title, content, publicDate, userID, status) 
VALUES ('Common Cold Remedies', 'How to treat the common cold at home...', '2023-09-26', 3, 1);
INSERT INTO [blog] (title, content, publicDate, userID, status) 
VALUES ('Herbal Supplements for Wellness', 'A review of the best herbal supplements...', '2023-09-27', 1, 1);
INSERT INTO [blog] (title, content, publicDate, userID, status) 
VALUES ('The Importance of Hydration', 'Staying hydrated for better health...', '2023-09-28', 2, 1);
INSERT INTO [blog] (title, content, publicDate, userID, status) 
VALUES ('Vaccination Myths Busted', 'Debunking common myths about vaccines...', '2023-09-29', 3, 1);
INSERT INTO [blog] (title, content, publicDate, userID, status) 
VALUES ('Understanding Mental Health', 'A guide to improving mental wellness...', '2023-09-30', 1, 1);
INSERT INTO [blog] (title, content, publicDate, userID, status) 
VALUES ('Top Skin Care Tips', 'Essential tips for healthy skin...', '2023-10-01', 2, 1);
INSERT INTO [blog] (title, content, publicDate, userID, status) 
VALUES ('Choosing the Right Medicine', 'How to select the appropriate medication...', '2023-10-02', 3, 1);
INSERT INTO [blog] (title, content, publicDate, userID, status) 
VALUES ('Benefits of Regular Exercise', 'How exercise improves overall health...', '2023-10-03', 1, 1);
GO
