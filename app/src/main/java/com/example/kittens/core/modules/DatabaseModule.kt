package com.example.kittens.core.modules

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kittens.data.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { get<AppDatabase>().catDao() }
    single { get<AppDatabase>().breedDao() }
    single { get<AppDatabase>().favouriteCatDao() }

    single {
        Room.databaseBuilder(
            get(), AppDatabase::class.java, "kittens-database"
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build()
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Create the Cat table
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `Cat` (
                `id` TEXT NOT NULL,
                `url` TEXT NOT NULL,
                `width` INTEGER NOT NULL,
                `height` INTEGER NOT NULL,
                PRIMARY KEY(`id`)
            )
            """.trimIndent()
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `Breed` (
                `id` TEXT NOT NULL,
                `name` TEXT NOT NULL,
                `temperament` TEXT NOT NULL,
                `origin` TEXT NOT NULL,
                `country_codes` TEXT NOT NULL,
                `country_code` TEXT NOT NULL,
                `life_span` TEXT,
                `wikipedia_url` TEXT,
                PRIMARY KEY(`id`)
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `CatBreedCrossRef` (
                `catId` TEXT NOT NULL,
                `breedId` TEXT NOT NULL,
                PRIMARY KEY(`catId`, `breedId`),
                FOREIGN KEY(`catId`) REFERENCES `Cat`(`id`) ON DELETE CASCADE,
                FOREIGN KEY(`breedId`) REFERENCES `Breed`(`id`) ON DELETE CASCADE
            )
            """.trimIndent()
        )

        db.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_CatBreedCrossRef_breedId` ON `CatBreedCrossRef` (`breedId`)"
        )
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `FavouriteCat` (
                `id` INTEGER NOT NULL,
                `userId` TEXT,
                `imageId` TEXT NOT NULL,
                `subId` TEXT,
                `createdAt` TEXT NOT NULL,
                `imageUrl` TEXT NOT NULL,
                PRIMARY KEY(`id`)
            )
            """.trimIndent()
        )
    }
}