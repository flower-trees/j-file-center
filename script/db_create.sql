/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

CREATE TABLE `file_info` (
                             `id` BIGINT NOT NULL COMMENT 'Primary key ID',
                             `name` VARCHAR(255) DEFAULT NULL COMMENT 'File name',
                             `size` INT DEFAULT NULL COMMENT 'File size in bytes',
                             `version` INT DEFAULT NULL COMMENT 'File version number',
                             `sha1` VARCHAR(64) DEFAULT NULL COMMENT 'SHA1 hash of the file',
                             `content_type` VARCHAR(100) DEFAULT NULL COMMENT 'File content type: image/jpeg, video/mp4, audio/mp3, application/pdf, etc.',
                             `file_status` INT DEFAULT NULL COMMENT 'File status: 0-updating, 1-update failed, 2-updated',
                             `user_id` BIGINT DEFAULT NULL COMMENT 'Uploader user ID',
                             `create_time` DATETIME DEFAULT NULL COMMENT 'Creation timestamp',
                             `update_id` BIGINT DEFAULT NULL COMMENT 'Updater user ID',
                             `update_time` DATETIME DEFAULT NULL COMMENT 'Last update timestamp',
                             `delete_flag` INT DEFAULT 0 COMMENT 'Logical delete flag: 0-not deleted, 1-deleted',
                             `url` VARCHAR(500) DEFAULT NULL COMMENT 'File access URL',
                             `source` INT DEFAULT 0 COMMENT 'File source: 0-common',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='File info table';

CREATE INDEX idx_user_id ON file_info(user_id);