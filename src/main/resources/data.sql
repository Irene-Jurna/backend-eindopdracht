-- Ingrediënten (basisgegevens voor álle ingrediënten)
INSERT INTO ingredients (id, name, created_date, last_modified_date)
VALUES
    (1, 'Gember', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'Citroen', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'Roze peper', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 'Suiker', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10, 'Colakruid', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- StoreIngredients (enkel extra kolommen)
INSERT INTO store_ingredients (id, point_of_sale)
VALUES
    (1, null),
    (2, 'Bio winkel'),
    (3, null),
    (4, null);

-- HarvestCrops (enkel extra kolommen)
INSERT INTO harvest_crops (id, about, harvest_method, storage_method)
VALUES
    (10,
     'Cola Kruid is een tamelijk bitter kruid, maar het heeft een heerlijke cola-achtige geur. Het kan worden gebruikt om een natuurlijke coladrank te maken - zie de receptinstructies onder. De prikkelende, geurende bladeren en bloemen worden gebruikt in kruidenthee en werden vroeger in kledingkasten gebruikt om motten af te weren. Jonge scheuten werden gebruikt om gebak en pudding op smaak te brengen. In Italië wordt het gebruikt als een culinair kruid.',
     'Oogst met een schaar',
     'Colakruid kan enkele dagen in vochtig keukenpapier in de koelkast bewaard worden, of in een gesloten zakje worden ingevroren.'
    );

-- Recipes
INSERT INTO recipes (id, title, description, cuisine_type, dish_type, created_date, last_modified_date)
VALUES (
           1,
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
    (1, 'Stap 1) Neem 500 ml basis waterkefir'),
    (1, 'Stap 2) Voeg colakruid, gember, roze peper, citroen en suiker toe'),
    (1, 'Stap 3) Wacht 24 uur'),
    (1, 'Stap 4) Zeef de ingredienten uit de kefir'),
    (1, 'Stap 5) Drink en geniet!');

-- Harvest Months
INSERT INTO recipe_harvest_month (recipe_id, harvest_month)
VALUES
    (1, 'MAR'), (1, 'APR'), (1, 'MAY'), (1, 'JUN'), (1, 'JUL'),
    (1, 'AUG'), (1, 'SEP'), (1, 'OCT'), (1, 'NOV');

-- IngredientUsages
INSERT INTO ingredient_usages (id, ingredient_id, recipe_id, quantity, unit, created_date, last_modified_date)
VALUES
    (1, 10, 1, 2, 'takjes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 1, 1, 5, 'plakjes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 3, 1, 7, 'korrels', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 2, 1, 2, 'schijfjes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 4, 1, 0, 'naar smaak (ik vind een halve eetlepel ruim voldoende)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
