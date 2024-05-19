/*
 Navicat Premium Data Transfer

 Source Server         : 小新小李
 Source Server Type    : PostgreSQL
 Source Server Version : 120012
 Source Host           : 114.132.67.226:5432
 Source Catalog        : edumindai
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 120012
 File Encoding         : 65001

 Date: 19/05/2024 16:51:43
*/


-- ----------------------------
-- Type structure for user_register_pattern
-- ----------------------------
DROP TYPE IF EXISTS "public"."user_register_pattern";
CREATE TYPE "public"."user_register_pattern" AS ENUM (
  'Phone',
  'Email'
);
ALTER TYPE "public"."user_register_pattern" OWNER TO "root";

-- ----------------------------
-- Type structure for user_status
-- ----------------------------
DROP TYPE IF EXISTS "public"."user_status";
CREATE TYPE "public"."user_status" AS ENUM (
  'NORMAL',
  'FROZEN',
  'DELETE'
);
ALTER TYPE "public"."user_status" OWNER TO "root";

-- ----------------------------
-- Type structure for user_type
-- ----------------------------
DROP TYPE IF EXISTS "public"."user_type";
CREATE TYPE "public"."user_type" AS ENUM (
  'ORDINARY',
  'VIP',
  'ADMIN'
);
ALTER TYPE "public"."user_type" OWNER TO "root";

-- ----------------------------
-- Table structure for user_topic_association
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_topic_association";
CREATE TABLE "public"."user_topic_association" (
  "id" uuid NOT NULL,
  "user_id" uuid,
  "topic_id" uuid,
  "create_at" timestamp(6) DEFAULT now(),
  "update_at" timestamp(6) DEFAULT now(),
  "title" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of user_topic_association
-- ----------------------------
INSERT INTO "public"."user_topic_association" VALUES ('43309ea5-965c-4912-862c-17024305e687', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', '43309ea5-965c-4912-862c-17024305e687', '2024-05-14 16:01:18.020751', '2024-05-14 16:01:18.020751', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('e0decba0-0d4e-4319-82e6-51cf9bc6e42b', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', 'e0decba0-0d4e-4319-82e6-51cf9bc6e42b', '2024-05-14 17:10:51.39746', '2024-05-14 17:10:51.39746', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('78592572-6205-4396-8136-eaf04ef86258', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', '78592572-6205-4396-8136-eaf04ef86258', '2024-05-14 17:21:42.553199', '2024-05-14 17:21:42.553199', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('5532f750-fe07-4a1e-ba1e-9f4beafba9a3', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', '5532f750-fe07-4a1e-ba1e-9f4beafba9a3', '2024-05-14 17:22:04.848266', '2024-05-14 17:22:04.848266', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('d2325161-4ca0-463c-9f76-969fcaca48a8', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', 'd2325161-4ca0-463c-9f76-969fcaca48a8', '2024-05-14 17:22:41.099016', '2024-05-14 17:22:41.099016', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('52f6d098-6d93-475a-b315-69416e0faad7', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', '52f6d098-6d93-475a-b315-69416e0faad7', '2024-05-14 17:22:41.162727', '2024-05-14 17:22:41.162727', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('dda02ef5-796e-4d95-8abd-66e9120ab021', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', 'dda02ef5-796e-4d95-8abd-66e9120ab021', '2024-05-14 17:24:51.023177', '2024-05-14 17:24:51.023177', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('5c5b6ba0-d184-4441-b50b-98033bbe5e89', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', '5c5b6ba0-d184-4441-b50b-98033bbe5e89', '2024-05-14 17:27:05.295445', '2024-05-14 17:27:05.295445', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('8c340b68-40ec-41b1-95e8-07242556a5ce', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', '8c340b68-40ec-41b1-95e8-07242556a5ce', '2024-05-14 17:27:45.557596', '2024-05-14 17:27:45.557596', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('c490d954-5a91-4ec1-8fb6-ec301058a6d1', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', 'c490d954-5a91-4ec1-8fb6-ec301058a6d1', '2024-05-14 17:28:15.326801', '2024-05-14 17:28:15.326801', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('391a67f3-d45a-4b16-a117-1a75e4f7a04c', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', '391a67f3-d45a-4b16-a117-1a75e4f7a04c', '2024-05-14 17:31:12.756648', '2024-05-14 17:31:12.756648', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('493bd88b-358e-4637-a5a9-f30ce8c560e7', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', '493bd88b-358e-4637-a5a9-f30ce8c560e7', '2024-05-14 17:33:38.214081', '2024-05-14 17:33:38.214081', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('8fbfa09e-052d-4bd9-a3bd-507b7600ba8f', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', '8fbfa09e-052d-4bd9-a3bd-507b7600ba8f', '2024-05-14 17:34:23.578714', '2024-05-14 17:34:23.578714', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('03b4860c-02d5-4722-854b-8ceaa5c3f1d8', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', '03b4860c-02d5-4722-854b-8ceaa5c3f1d8', '2024-05-14 17:35:31.605704', '2024-05-14 17:35:31.605704', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('84f4369a-f559-412a-9030-6538b4222d0b', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', '84f4369a-f559-412a-9030-6538b4222d0b', '2024-05-14 17:39:10.295729', '2024-05-14 17:39:10.295729', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('ab0ccd43-2916-4201-9a07-5b7330aa9a26', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', 'ab0ccd43-2916-4201-9a07-5b7330aa9a26', '2024-05-14 17:51:48.744634', '2024-05-14 17:51:48.744634', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('9b076525-20a8-4b32-8278-b223001f4cf2', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', '9b076525-20a8-4b32-8278-b223001f4cf2', '2024-05-15 10:49:54.661945', '2024-05-15 10:49:54.661945', 'New Connect');
INSERT INTO "public"."user_topic_association" VALUES ('2ebc6d19-0680-4d37-9622-37cebbd796cc', '6afec9c4-66ef-4dc1-94ed-99441d3484cb', '2ebc6d19-0680-4d37-9622-37cebbd796cc', '2024-05-16 09:16:28.706041', '2024-05-16 09:16:28.706041', 'New Connect');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS "public"."users";
CREATE TABLE "public"."users" (
  "id" uuid NOT NULL,
  "nickname" varchar(25) COLLATE "pg_catalog"."default",
  "avatar" varchar(255) COLLATE "pg_catalog"."default",
  "types" "public"."user_type",
  "status" "public"."user_status",
  "email" varchar(50) COLLATE "pg_catalog"."default",
  "phone" varchar(25) COLLATE "pg_catalog"."default",
  "create_at" timestamp(6) DEFAULT now(),
  "update_at" timestamp(6) DEFAULT now(),
  "password" varchar(255) COLLATE "pg_catalog"."default",
  "register_pattern" "public"."user_register_pattern"
)
;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO "public"."users" VALUES ('6afec9c4-66ef-4dc1-94ed-99441d3484cb', NULL, NULL, 'ORDINARY', 'NORMAL', 'ljzcomeon@gmail.com', NULL, '2024-05-02 15:27:22.492277', '2024-05-02 15:27:22.492277', '$2a$10$zh6hBmd6G3WgMZL8NDR6u.pDbh3MKo41HHZY0HQGB/znK2JkJIKZa', 'Email');

-- ----------------------------
-- Primary Key structure for table user_topic_association
-- ----------------------------
ALTER TABLE "public"."user_topic_association" ADD CONSTRAINT "user_topic_association_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table users
-- ----------------------------
ALTER TABLE "public"."users" ADD CONSTRAINT "users_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table user_topic_association
-- ----------------------------
ALTER TABLE "public"."user_topic_association" ADD CONSTRAINT "user_id" FOREIGN KEY ("user_id") REFERENCES "public"."users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
