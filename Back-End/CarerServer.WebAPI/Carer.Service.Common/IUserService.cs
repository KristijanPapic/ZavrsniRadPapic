using Carer.Model.DomainModels;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Carer.Service.Common
{
    public interface IUserService
    {
        Task DeleteUserAsync(Guid id);
        Task<List<UserDomainModel>> GetAllUserAsync();
        Task<UserDomainModel> GetUserByIdAsync(Guid id);
        Task PostUserAsync(UserDomainModel user);
        Task PutUserAsync(UserDomainModel user);

        Task AddOwnerReview(OwnerReviewDomainModel review);
        Task AddCarerReview(CarerReviewDomainModel review);
    }
}