using Carer.Common;
using Carer.Model;
using Carer.Model.DomainModels;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Carer.Repository
{
    public interface IAppliedUserRepository
    {
        Task DeleteAppliedUserAsync(Guid userId, Guid adId);
        Task<List<AppliedUserDomainModel>> GetAllAppliedUsersAsync(AppliedUserFilter filter);
        Task PostAppliedUserAsync(AppliedUserDomainModel appliedUser);

        Task PutAppliedUserAsync(Guid userId, Guid adId, string status);

        Task<int> GetAppliedUserCountAsync(Guid adId);
        Task<string> GetAppliedUserStatusAsync(Guid userId, Guid adId);

        Task<SimpleAppliedUser> GetAppliedUserAsync(Guid adId);
    }
}