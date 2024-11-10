package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.ReviewDao
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.ReviewRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReviewDTO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response
import java.time.LocalDate

class ReviewRepositoryTest {

    @Test
    fun addReseña() = runTest {
        // Given
        val review = ReviewDTO(
            resenaId = 2,
            usuarioId = 2,
            comentario = "comentario 2",
            fechaResena = LocalDate.now().toString(),
            calificacion = 2
        )

        val reviewRemoteDataSource = mockk<ReviewRemoteDataSource>()
        val reviewDao = mockk<ReviewDao>()
        val repository = ReviewRepository(reviewRemoteDataSource, reviewDao)

        coEvery { reviewRemoteDataSource.postReseña(any()) } returns review

        // When
        repository.addReseña(review)

        // Then
        coVerify { reviewRemoteDataSource.postReseña(review) }
    }

    @Test
    fun getReseña() = runTest {
        // Given
        val review = ReviewDTO(
            resenaId = 2,
            usuarioId = 2,
            comentario = "comentario 2",
            fechaResena = LocalDate.now().toString(),
            calificacion = 2
        )

        val reviewRemoteDataSource = mockk<ReviewRemoteDataSource>()
        val reviewDao = mockk<ReviewDao>()
        val repository = ReviewRepository(reviewRemoteDataSource, reviewDao)

        coEvery { reviewRemoteDataSource.getReseñaById(review.resenaId) } returns review

        // When
        val result = repository.getReseña(review.resenaId)

        // Then
        coVerify { reviewRemoteDataSource.getReseñaById(review.resenaId) }
        assertEquals(review, result)
    }

    @Test
    fun deleteReseña() = runTest {
        // Given
        val reviewId = 2

        val reviewRemoteDataSource = mockk<ReviewRemoteDataSource>()
        val reviewDao = mockk<ReviewDao>()
        val repository = ReviewRepository(reviewRemoteDataSource, reviewDao)

        coEvery { reviewRemoteDataSource.deleteReseña(reviewId) } returns Response.success(Unit)

        // When:
        repository.deleteReseña(reviewId)

        // Then:
        coVerify { reviewRemoteDataSource.deleteReseña(reviewId) }
    }



    @Test
    fun updateReseña() = runTest {
        // Given
        val review = ReviewDTO(
            resenaId = 2,
            usuarioId = 2,
            comentario = "comentario actualizado",
            fechaResena = LocalDate.now().toString(),
            calificacion = 4
        )

        val reviewRemoteDataSource = mockk<ReviewRemoteDataSource>()
        val reviewDao = mockk<ReviewDao>()
        val repository = ReviewRepository(reviewRemoteDataSource, reviewDao)

        coEvery { reviewRemoteDataSource.putReseña(review.resenaId ?: 0, review) } returns review

        // When
        repository.updateReseña(review.resenaId ?: 0, review)

        // Then
        coVerify { reviewRemoteDataSource.putReseña(review.resenaId ?: 0, review) }
    }

    @Test
    fun getReseñas() = runTest {
        // Given
        val reviews = listOf(
            ReviewDTO(
                resenaId = 1,
                usuarioId = 1,
                comentario = "comentario 1",
                fechaResena = LocalDate.now().toString(),
                calificacion = 3
            ),
            ReviewDTO(
                resenaId = 2,
                usuarioId = 2,
                comentario = "comentario 2",
                fechaResena = LocalDate.now().toString(),
                calificacion = 5
            )
        )

        val reviewRemoteDataSource = mockk<ReviewRemoteDataSource>()
        val reviewDao = mockk<ReviewDao>()
        val repository = ReviewRepository(reviewRemoteDataSource, reviewDao)

        coEvery { reviewRemoteDataSource.getReseñas() } returns reviews

        // When
        val result = repository.getReseñas()

        // Then
        coVerify { reviewRemoteDataSource.getReseñas() }
        assertEquals(reviews, result)
    }
}
