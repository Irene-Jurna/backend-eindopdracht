-- Ingrediënten (basisgegevens voor álle ingrediënten)
INSERT INTO ingredients (id, name, created_date, last_modified_date)
VALUES
    (1001, 'Gember', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1002, 'Citroen', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1003, 'Roze peper', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1004, 'Suiker', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1010, 'Colakruid', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- StoreIngredients (enkel extra kolommen)
INSERT INTO store_ingredients (id, point_of_sale)
VALUES
    (1001, null),
    (1002, 'Bio winkel'),
    (1003, null),
    (1004, null);

-- HarvestCrops (enkel extra kolommen)
INSERT INTO harvest_crops (id, about, harvest_method, storage_method)
VALUES
    (1010,
     'Cola Kruid is een tamelijk bitter kruid, maar het heeft een heerlijke cola-achtige geur. Het kan worden gebruikt om een natuurlijke coladrank te maken - zie de receptinstructies onder. De prikkelende, geurende bladeren en bloemen worden gebruikt in kruidenthee en werden vroeger in kledingkasten gebruikt om motten af te weren. Jonge scheuten werden gebruikt om gebak en pudding op smaak te brengen. In Italië wordt het gebruikt als een culinair kruid.',
     'Oogst met een schaar',
     'Colakruid kan enkele dagen in vochtig keukenpapier in de koelkast bewaard worden, of in een gesloten zakje worden ingevroren.'
    );

-- Recipes
INSERT INTO recipes (id, title, description, cuisine_type, dish_type, created_date, last_modified_date)
VALUES (
           1001,
           'Colakruid waterkefir',
           'De colaplant kan een wrange smaak hebben als je het in recepten gebruikt. Maar voor de waterkefir is het een perfect kruid. Dit is mijn lievelingsrecept (en je maakt het in 5 minuten).',
           'VEGETARIAN',
           'DRINK',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

-- Cooking Steps
INSERT INTO recipe_cooking_steps (recipe_id, cooking_steps)
VALUES
    (1001, 'Stap 1) Neem 500 ml basis waterkefir'),
    (1001, 'Stap 2) Voeg colakruid, gember, roze peper, citroen en suiker toe'),
    (1001, 'Stap 3) Wacht 24 uur'),
    (1001, 'Stap 4) Zeef de ingredienten uit de kefir'),
    (1001, 'Stap 5) Drink en geniet!');

-- Harvest Months
INSERT INTO recipe_harvest_month (recipe_id, harvest_month)
VALUES
    (1001, 'MAR'), (1001, 'APR'), (1001, 'MAY'), (1001, 'JUN'), (1001, 'JUL'),
    (1001, 'AUG'), (1001, 'SEP'), (1001, 'OCT'), (1001, 'NOV');

-- IngredientUsages
INSERT INTO ingredient_usages (id, ingredient_id, recipe_id, quantity, unit, created_date, last_modified_date)
VALUES
    (1001, 1010, 1001, 2, 'takjes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1002, 1001, 1001, 5, 'plakjes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1003, 1003, 1001, 7, 'korrels', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1004, 1002, 1001, 2, 'schijfjes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1005, 1004, 1001, 0, 'naar smaak (ik vind een halve eetlepel ruim voldoende)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (id, first_name, last_name, email, password, role, subscription_type, volunteer, created_date, last_modified_date)
VALUES
    -- Wachtwoord: KomKommerKruid1!
    (
        1100,
        'Boerin',
        'Barbara',
        'barb@pluk.nl',
        '$2b$12$VO0SVhp1D.gOO4wXEIOJyOSh2toTK3wnuoQVfbdBfjZD4nmzzGXJ.',
        'FARMER',
        null,
        false,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );
