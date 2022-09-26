using Carer.Model.DomainModels;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Carer.Repository.Common
{
    public interface IUserRepository
    {
        Task DeleteUserAsync(Guid id);
        Task<List<UserDomainModel>> GetAllUsersAsync();
        Task<Guid> GetUserByEmail(string email);
        Task<UserDomainModel> GetUserByIdAsync(Guid id);
        Task PostUserAsync(UserDomainModel user);
        Task PutUserAsync(UserDomainModel user);

        void AddOwnerReview(OwnerReviewDomainModel review);

        void AddCarerReview(CarerReviewDomainModel review);
    }
}