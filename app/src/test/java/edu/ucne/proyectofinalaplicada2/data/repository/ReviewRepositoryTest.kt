package edu.ucne.proyectofinalaplicada2.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.proyectofinalaplicada2.data.local.dao.ReviewDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReviewEntity
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.ReviewRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReviewDTO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate

class ReviewRepositoryTest {

    @Test
    fun addReview() = runTest {
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

        coEvery { reviewRemoteDataSource.postReview(any()) } returns review

        // When
        repository.addReview(review)

        // Then
        coVerify { reviewRemoteDataSource.postReview(review) }
    }

    @Test
    fun getReview() = runTest {
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

        coEvery { reviewRemoteDataSource.getReviewById(review.resenaId) } returns review

        // When
        val result = repository.getReview(review.resenaId)

        // Then
        coVerify { reviewRemoteDataSource.getReviewById(review.resenaId) }
        assertEquals(review, result)
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

        coEvery { reviewRemoteDataSource.putReview(review.resenaId ?: 0, review) } returns review

        // When
        repository.updateReview(review.resenaId ?: 0, review)

        // Then
        coVerify { reviewRemoteDataSource.putReview(review.resenaId ?: 0, review) }
    }

    @Test
    fun getReseñas() = runTest {
        // Given
        val reviews = listOf(
            ReviewEntity(
                resenaId = 2,
                usuarioId = 2,
                comentario = "comentario 2",
                fechaResena = LocalDate.now().toString(),
                calificacion = 2
            )
        )

        val reviewRemoteDataSource = mockk<ReviewRemoteDataSource>()
        val reviewDao = mockk<ReviewDao>()
        val repository = ReviewRepository(reviewRemoteDataSource, reviewDao)

       coEvery { reviewDao.getAll() } returns flowOf(reviews)

        // When
        repository.getReseñas().test {
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            val result = awaitItem()
            Truth.assertThat(result).isInstanceOf(Resource.Success::class.java)
            Truth.assertThat(result.data).isEqualTo(reviews)

            cancelAndIgnoreRemainingEvents()
        }
    }


}
